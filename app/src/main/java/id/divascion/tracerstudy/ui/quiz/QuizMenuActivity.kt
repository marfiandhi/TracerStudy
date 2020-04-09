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
import id.divascion.tracerstudy.data.presenter.PresenterData
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
    private lateinit var presenter: PresenterData
    private lateinit var uid: String
    private var menuClicked = false
    private var list = ArrayList<String>()
    private var already = 0
    private var pause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.prompt_quiz)
        role = intent.getStringExtra("ROLE")
        user = FirebaseAuth.getInstance().currentUser!!
        uid = user.uid
        mDatabase = FirebaseDatabase.getInstance().reference
        presenter = PresenterData(mDatabase)
        when (role) {
            "alumni" -> {
                supportActionBar?.title = getString(R.string.prompt_quiz_study)
                titles = resources.getStringArray(R.array.list_quiz_alumni_subtitle)
                quiz_menu_save_button.setOnClickListener {
                    val list = SharedPreferenceManager().getListRole(this, role, uid) ?: ArrayList()
                    var valid = true
                    if (list.size > 0) {
                        for (i in list) {
                            if (i.equals("none", true)) {
                                valid = false
                            }
                        }
                    } else {
                        valid = false
                    }
                    if (valid) {
                        val data = SharedPreferenceManager().getAlumni(this, uid)
                        presenter.addData(this, data, uid)
                    } else {
                        toast("Harap lengkapi semua kuesioner sebelum menyimpan")
                    }
                }
            }
            "stakeholder" -> {
                supportActionBar?.title = getString(R.string.prompt_quiz_stakeholder)
                titles = resources.getStringArray(R.array.list_quiz_stakeholder_subtitle)
                quiz_menu_save_button.setOnClickListener {
                    val list = SharedPreferenceManager().getListRole(this, role, uid) ?: ArrayList()
                    var valid = true
                    if (list.size > 0) {
                        for (i in list) {
                            if (i.equals("none", true)) {
                                valid = false
                            }
                        }
                    } else {
                        valid = false
                    }
                    if (valid) {
                        val data = SharedPreferenceManager().getStake(this, uid)
                        presenter.addData(this, data, uid)
                    } else {
                        toast("Harap lengkapi semua kuesioner sebelum menyimpan")
                    }
                }
            }
            else -> {
                quiz_menu_save_button.isEnabled = false
            }
        }
        loadDataAdapter()
        adapter = QuizMenuAdapter(this, titles, this.list) { i: Int, s: String, l: String ->
            menuClicked = true
            startActivity<QuizActivity>(
                "QUIZ_NUMBER" to i,
                "QUIZ_TITLE" to s,
                "ROLE" to role,
                "STATUS" to l
            )
        }
        quiz_menu_rv.layoutManager = LinearLayoutManager(this)
        quiz_menu_rv.adapter = adapter
    }

    override fun onPause() {
        pause = true
        if (menuClicked) {
            showLoading()
            menuClicked = false
        }
        super.onPause()
    }

    override fun onResume() {
        if (pause) {
            hideLoading("")
            loadDataAdapter()
            adapter.notifyDataSetChanged()
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

    private fun loadDataAdapter() {
        val list = SharedPreferenceManager().getListRole(this, role, uid) ?: ArrayList()
        already++
        try {
            if (list.size > 0) {
                Log.e("List quiz", "Size is not 0")
                this.list.clear()
                this.list.addAll(list)
            } else if (already == 1) {
                if (role == "alumni") {
                    for (i in 0..5) {
                        this.list.add("none")
                    }
                    presenter.getDataAlumni(this, uid)
                } else if (role == "stakeholder") {
                    for (i in 0..3) {
                        this.list.add("none")
                    }
                    presenter.getDataStake(this, uid)
                }
            }
        } catch (e: Exception) {
            Log.e("Quiz Menu", e.message)
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

    override fun getData(data: AlumniQuiz?) {
        if (data != null) {
            SharedPreferenceManager().saveAlumni(this, data, uid)
            loadDataAdapter()
        }
    }

    override fun getData(data: StakeQuiz?) {
        if (data != null) {
            SharedPreferenceManager().saveStake(this, data, uid)
            loadDataAdapter()
        }
    }
}
