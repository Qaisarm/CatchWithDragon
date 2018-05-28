package dragongame.qaisarm.kth.com.catchdragons

import android.content.Intent
import android.graphics.Point
import android.os.Handler
import android.support.v4.view.MotionEventCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import java.util.Timer
import java.util.TimerTask

class Main : AppCompatActivity() {

    private var scoreLabel: TextView? = null
    private var startLabel: TextView? = null
    private var box: ImageView? = null
    private var black: ImageView? = null
    private var orange: ImageView? = null
    private var red: ImageView? = null

    // size

    private var frameHeight: Int = 0
    private var boxSize: Int = 0
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0


    // Position
    private var boxY: Int = 0
    private var orangeX: Int = 0
    private var orangeY: Int = 0
    private var redX: Int = 0
    private var redY: Int = 0
    private var blackX: Int = 0
    private var blackY: Int = 0

    // speed

    private var boxSpeed: Int = 0
    private var orangeSpeed: Int = 0
    private var redSpeed: Int = 0
    private var blackSpeed: Int = 0


    private var score = 0

    // Initialize Class
    private val handler = Handler()
    private val timer = Timer()
    private var sound: SoundPlayer? = null

    // Status Check
    private var action_flag = false
    private var start_flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sound = SoundPlayer(this)

        scoreLabel = findViewById<View>(R.id.scoreLabel) as TextView
        startLabel = findViewById<View>(R.id.startLabel) as TextView

        box = findViewById<View>(R.id.box) as ImageView
        orange = findViewById<View>(R.id.orange) as ImageView
        black = findViewById<View>(R.id.black) as ImageView
        red = findViewById<View>(R.id.red) as ImageView


        // get Screen Size

        val wm = windowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        screenWidth = size.x
        screenHeight = size.y

        boxSpeed = Math.round(screenHeight / 60f)
        orangeSpeed = Math.round(screenWidth / 60f)
        redSpeed = Math.round(screenWidth / 36f)
        blackSpeed = Math.round(screenWidth / 45f)
        Log.v("SPEED_BOX", boxSpeed.toString() + "")
        Log.v("SPEED_ORANGE", orangeSpeed.toString() + "")
        Log.v("SPEED_RED", redSpeed.toString() + "")
        Log.v("SPEED_BLACK", blackSpeed.toString() + "")

        // move to out screen
        orange !!.x = - 80f
        orange !!.y = - 80f
        red !!.x = - 80f
        red !!.y = - 80f
        black !!.x = - 80f
        black !!.y = - 80f

        scoreLabel !!.text = "Score : 0"


    }

    fun changePosition() {

        hitCheck()

        // orange
        orangeX -= orangeSpeed

        if (orangeX < 0) {
            orangeX = screenWidth + 20
            orangeY = Math.floor(Math.random() * (frameHeight - orange !!.height)).toInt()
        }
        orange !!.x = orangeX.toFloat()
        orange !!.y = orangeY.toFloat()

        // black
        blackX -= blackSpeed

        if (blackX < 0) {
            blackX = screenWidth + 20
            blackY = Math.floor(Math.random() * (frameHeight - black !!.height)).toInt()
        }
        black !!.x = blackX.toFloat()
        black !!.y = blackY.toFloat()

        // red
        redX -= redSpeed

        if (redX < 0) {
            redX = screenWidth + 20
            redY = Math.floor(Math.random() * (frameHeight - red !!.height)).toInt()
        }
        red !!.x = redX.toFloat()
        red !!.y = redY.toFloat()

        // Move box

        if (action_flag) {

            // Touching
            boxY -= 20
        } else {
            boxY += 20
        }
        // check box position

        if (boxY < 0) boxY = 0
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize
        box !!.y = boxY.toFloat()

        scoreLabel !!.text = "Score : $score"
    }

    private fun hitCheck() {
        // orange
        val orangeCenterX = orangeX + orange !!.width / 2
        val orangeCenterY = orangeY + orange !!.height / 2

        if (orangeCenterX in 0..boxSize &&
                boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize) {

            score += 10
            orangeX = - 10

            sound !!.playHitSound()
        }

        // red
        val redCenterX = redX + red !!.width / 2
        val redCenterY = redY + red !!.height / 2

        if (redCenterX in 0..boxSize &&
                boxY <= redCenterY && redCenterY <= boxY + boxSize) {

            score += 20
            redX = - 10
            sound !!.playHitSound()
        }

        // red
        val blackCenterX = blackX + black !!.width / 2
        val blackCenterY = blackY + black !!.height / 2

        if (blackCenterX in 0..boxSize &&
                boxY <= blackCenterY && blackCenterY <= boxY + boxSize) {

            timer.cancel()
            //timer= null;

            sound !!.playOverSound()

            // Show Result
            val intent = Intent(applicationContext, Result::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        if (!start_flag) {
            start_flag = true

            val frame = findViewById<View>(R.id.frame) as FrameLayout
            frameHeight = frame.height

            boxY = box !!.y.toInt()

            boxSize = box !!.height


            startLabel !!.visibility = View.GONE
            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post { changePosition() }

                }
            }, 0, 20)

        } else {
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                action_flag = true
            } else if (motionEvent.action == MotionEvent.ACTION_UP) {
                action_flag = false
            }

        }

        return true
    }

}
