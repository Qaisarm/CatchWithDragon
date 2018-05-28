package dragongame.qaisarm.kth.com.catchdragons

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class Result : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val scoreLable = findViewById<View>(R.id.scoreLabel) as TextView
        val highScoreLable = findViewById<View>(R.id.highScoreLable) as TextView
        val score = intent.getIntExtra("SCORE", 0)
        scoreLable.text = "$score "

        val settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE)
        val highScore = settings.getInt("HIGH_SCORE", 0)

        if (score > highScore) {
            highScoreLable.text = "High Score : $score"

            //save
            val editor = settings.edit()
            editor.putInt("HIGH_SCORE", score)
            editor.apply()
        } else {
            highScoreLable.text = "High Score : $highScore"

        }
    }

    fun tryAgain(view: View) {
        startActivity(Intent(applicationContext, Main::class.java))
    }
}
