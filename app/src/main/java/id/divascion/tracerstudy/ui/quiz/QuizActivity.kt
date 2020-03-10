package id.divascion.tracerstudy.ui.quiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizOne
import id.divascion.tracerstudy.ui.quiz.fragment.AlumniOneFragment
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class QuizActivity : AppCompatActivity(), AlumniOneFragment.OnFragmentInteractionListener {

    private lateinit var extractTitle: String
    private lateinit var quizTitle: String
    private lateinit var role: String
    private lateinit var status: String
    private lateinit var user: FirebaseUser
    private var stringManipulation = StringManipulation()
    private var number: Int = -1
    private var mSavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        mSavedInstanceState = savedInstanceState
        user = FirebaseAuth.getInstance().currentUser!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        number = intent.getIntExtra("QUIZ_NUMBER", -1)+1
        quizTitle = intent.getStringExtra("QUIZ_TITLE")
        role = intent.getStringExtra("ROLE")
        status = intent.getStringExtra("STATUS")
        loadQuizFragment()
        extractTitle = stringManipulation.listExtracted(quizTitle)
        supportActionBar?.title = extractTitle
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadQuizFragment() {
        when {
            role.equals("alumni", true) -> when(number) {
                1 -> changeFragment(mSavedInstanceState, AlumniOneFragment())
                2 -> /*changeFragment(mSavedInstanceState, AlumniTwoFragment())*/toast(number)
                3 -> /*changeFragment(mSavedInstanceState, AlumniThreeFragment())*/toast(number)
                4 -> /*changeFragment(mSavedInstanceState, AlumniFourFragment())*/toast(number)
                5 -> /*changeFragment(mSavedInstanceState, AlumniFiveFragment())*/toast(number)
                6 -> /*changeFragment(mSavedInstanceState, AlumniSixFragment())*/toast(number)
            }
            role.equals("stakeholder", true) -> when(number){
                1 -> /*changeFragment(mSavedInstanceState, StakeholderOneFragment())*/toast(number)
                2 -> /*changeFragment(mSavedInstanceState, StakeholderTwoFragment())*/toast(number)
                4 -> /*changeFragment(mSavedInstanceState, StakeholderThreeFragment())*/toast(number)
                5 -> /*changeFragment(mSavedInstanceState, StakeholderFourFragment())*/toast(number)
                else -> longToast("Terjadi kesalahan pada nomor pertanyaan")
            }
            else -> longToast("Terjadi kesalahan pada role Anda")
        }
    }

    private fun changeFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if(savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putParcelable("USER", user)
            bundle.putString("STATUS", status)
            fragment.arguments = bundle
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.quiz_frame, fragment)
                .commit()
        }
        mSavedInstanceState = savedInstanceState
    }

    override fun onFragmentInteractionAlumniOne(data: AlumniQuizOne) {
        SharedPreferenceManager().saveAlumniOne(this, data, user.uid)
        finish()
    }

}
