package id.divascion.tracerstudy.ui.quiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.adapter.QuizMenuAdapter
import id.divascion.tracerstudy.data.model.AlumniQuiz
import id.divascion.tracerstudy.data.model.StakeQuiz
import id.divascion.tracerstudy.data.presenter.PresenterQuiz
import id.divascion.tracerstudy.util.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_quiz_menu.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class QuizMenuActivity : AppCompatActivity(), QuizMenuView {

    private lateinit var role: String
    private lateinit var titles: Array<String>
    private lateinit var adapter: QuizMenuAdapter
    private lateinit var user: FirebaseUser
    private lateinit var mDatabase: DatabaseReference
    private lateinit var presenter: PresenterQuiz
    private lateinit var uid: String
    private var pause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.prompt_quizioner)
        role = intent.getStringExtra("ROLE")
        user = FirebaseAuth.getInstance().currentUser!!
        uid = user.uid
        mDatabase = FirebaseDatabase.getInstance().reference
        presenter = PresenterQuiz(mDatabase)
        when (role) {
            "alumni" -> {
                titles = resources.getStringArray(R.array.list_quiz_alumni_subtitle)
                quiz_menu_save_button.setOnClickListener {
                    val data = SharedPreferenceManager().getAlumni(this, uid)
                    presenter.addData(this, data, uid)
                }
            }
            "stakeholder" -> {
                titles = resources.getStringArray(R.array.list_quiz_stakeholder_subtitle)
                quiz_menu_save_button.setOnClickListener {
                    val data = SharedPreferenceManager().getStake(this, uid)
                    presenter.addData(this, data, uid)
                }
            }
            else -> {
                quiz_menu_save_button.isEnabled = false
            }
        }
        initUI()
    }

    override fun onPause() {
        pause = true
        super.onPause()
    }

    override fun onResume() {
        if (pause) {
            loadDataAdapter()
            adapter.notifyDataSetChanged()
            quiz_menu_rv.layoutManager = LinearLayoutManager(this)
            quiz_menu_rv.adapter = adapter
            pause = false
        }
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        loadDataAdapter()
        quiz_menu_rv.layoutManager = LinearLayoutManager(this)
        quiz_menu_rv.adapter = adapter
    }

    private fun loadDataAdapter() {
        val list = SharedPreferenceManager().getListRole(this, role, uid) ?: ArrayList()
        try {
            if (list.size > 0) {
                Log.e("List quiz", "Size is not 0")
            } else {
                for (i in 0..5) {
                    list.add("none")
                }
                if (role == "alumni") {
                    presenter.getDataAlumni(this, uid)

                } else if (role == "stakeholder") {
                    presenter.getDataStake(this, uid)
                }
            }
        } catch (e: Exception) {
            for (i in 0..5) {
                list.add("none")
            }
            if (role == "alumni") {
                presenter.getDataAlumni(this, uid)

            } else if (role == "stakeholder") {
                presenter.getDataStake(this, uid)
            }
        }
        adapter = QuizMenuAdapter(this, titles, list) { i: Int, s: String, l: String ->
            startActivity<QuizActivity>(
                "QUIZ_NUMBER" to i,
                "QUIZ_TITLE" to s,
                "ROLE" to role,
                "STATUS" to l
            )
        }
    }

    override fun showLoading() {
        quiz_menu_loading.visibility = View.VISIBLE
    }

    override fun hideLoading(message: String) {
        quiz_menu_loading.visibility = View.GONE
        if (message.isNotEmpty()) {
            toast(message)
        }
    }

    override fun getData(data: AlumniQuiz) {
        SharedPreferenceManager().saveAlumni(this, data, uid)
        initUI()
    }

    override fun getData(data: StakeQuiz) {
        SharedPreferenceManager().saveStake(this, data, uid)
        initUI()
    }
}
