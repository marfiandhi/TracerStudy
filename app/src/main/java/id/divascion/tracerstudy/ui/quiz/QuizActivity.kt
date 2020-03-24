package id.divascion.tracerstudy.ui.quiz

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.*
import id.divascion.tracerstudy.ui.quiz.fragment.*
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import org.jetbrains.anko.longToast

class QuizActivity : AppCompatActivity(), AlumniOneFragment.OnFragmentInteractionListener,
    AlumniTwoFragment.OnFragmentInteractionListener,
    AlumniThreeFragment.OnFragmentInteractionListener,
    AlumniFourFragment.OnFragmentInteractionListener,
    AlumniFiveFragment.OnFragmentInteractionListener,
    AlumniSixFragment.OnFragmentInteractionListener,
    StakeOneFragment.OnFragmentInteractionListener,
    StakeTwoFragment.OnFragmentInteractionListener,
    StakeThreeFragment.OnFragmentInteractionListener,
    StakeFourFragment.OnFragmentInteractionListener {

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
        number = intent.getIntExtra("QUIZ_NUMBER", -1) + 1
        quizTitle = intent.getStringExtra("QUIZ_TITLE")
        role = intent.getStringExtra("ROLE")
        status = intent.getStringExtra("STATUS")
        loadQuizFragment()
        extractTitle = stringManipulation.listExtracted(quizTitle)
        supportActionBar?.title = extractTitle
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadQuizFragment() {
        when {
            role.equals("alumni", true) -> when (number) {
                1 -> changeFragment(mSavedInstanceState, AlumniOneFragment())
                2 -> changeFragment(mSavedInstanceState, AlumniTwoFragment())
                3 -> changeFragment(mSavedInstanceState, AlumniThreeFragment())
                4 -> changeFragment(mSavedInstanceState, AlumniFourFragment())
                5 -> changeFragment(mSavedInstanceState, AlumniFiveFragment())
                6 -> changeFragment(mSavedInstanceState, AlumniSixFragment())
            }
            role.equals("stakeholder", true) -> when (number) {
                1 -> changeFragment(mSavedInstanceState, StakeOneFragment())
                2 -> changeFragment(mSavedInstanceState, StakeTwoFragment())
                3 -> changeFragment(mSavedInstanceState, StakeThreeFragment())
                4 -> changeFragment(mSavedInstanceState, StakeFourFragment())
                else -> longToast("Terjadi kesalahan pada nomor pertanyaan")
            }
            else -> longToast("Terjadi kesalahan pada role Anda")
        }
    }

    private fun changeFragment(savedInstanceState: Bundle?, fragment: Fragment) {
        if (savedInstanceState == null) {
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

    override fun onFragmentInteraction(data: AlumniQuizOne) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: AlumniQuizTwo) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: AlumniQuizThree) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: AlumniQuizFour) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: AlumniQuizFive) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: AlumniQuizSix) {
        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: StakeQuizOne) {
//        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: StakeQuizTwo) {
//        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: StakeQuizThree) {
//        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

    override fun onFragmentInteraction(data: StakeQuizFour) {
//        SharedPreferenceManager().saveAlumni(this, data, user.uid)
        finish()
    }

}
