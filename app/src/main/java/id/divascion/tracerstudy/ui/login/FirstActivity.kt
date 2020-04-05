package id.divascion.tracerstudy.ui.login

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import id.divascion.tracerstudy.R
import org.jetbrains.anko.startActivity

private const val SPLASH_DISPLAY_LENGTH = 2000

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        Handler().postDelayed({ move() }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    private fun move() {
        startActivity<LoginActivity>()
        finish()
    }
}
