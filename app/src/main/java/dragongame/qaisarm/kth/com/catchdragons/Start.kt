package dragongame.qaisarm.kth.com.catchdragons

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun startGame(view: View) {
        startActivity(Intent(applicationContext, Main::class.java))
    }
}
