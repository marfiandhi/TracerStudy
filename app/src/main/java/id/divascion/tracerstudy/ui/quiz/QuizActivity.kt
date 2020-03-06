package id.divascion.tracerstudy.ui.quiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.util.StringManipulation
import org.jetbrains.anko.toast

class QuizActivity : AppCompatActivity() {

    private lateinit var extractTitle: String
    private lateinit var quizTitle: String
    private lateinit var role: String
    private var stringManipulation = StringManipulation()
    private var number: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        number = intent.getIntExtra("QUIZ_NUMBER", -1)
        quizTitle = intent.getStringExtra("QUIZ_TITLE")
        role = intent.getStringExtra("ROLE")
        extractTitle = stringManipulation.listExtracted(quizTitle)
        supportActionBar?.title = extractTitle
        toast("$number dan $role")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
