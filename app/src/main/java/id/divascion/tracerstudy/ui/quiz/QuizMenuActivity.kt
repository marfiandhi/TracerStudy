package id.divascion.tracerstudy.ui.quiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.adapter.QuizMenuAdapter
import kotlinx.android.synthetic.main.activity_quiz_menu.*
import org.jetbrains.anko.startActivity

class QuizMenuActivity : AppCompatActivity() {

    private lateinit var role: String
    private lateinit var titles: Array<String>
    private lateinit var adapter: QuizMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.prompt_quizioner)
        role = intent.getStringExtra("ROLE")
        when(role) {
            "alumni" -> {
                titles = resources.getStringArray(R.array.list_quiz_alumni_subtitle)
            }
            "stakeholder" -> {
                titles = resources.getStringArray(R.array.list_quiz_stakeholder_subtitle)
            }
            else -> {
                quiz_menu_save_button.isEnabled = false
            }
        }
        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        adapter = QuizMenuAdapter(this, titles){ i: Int, s: String ->
            startActivity<QuizActivity>("QUIZ_NUMBER" to i, "QUIZ_TITLE" to s, "ROLE" to role)
        }
        quiz_menu_rv.layoutManager = LinearLayoutManager(this)
        quiz_menu_rv.adapter = adapter
    }
}
