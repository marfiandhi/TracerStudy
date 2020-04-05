package id.divascion.tracerstudy.ui.alumni

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log.e
import android.view.MenuItem
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.adapter.PagerAdapter
import id.divascion.tracerstudy.data.model.*
import id.divascion.tracerstudy.data.presenter.PresenterQuiz
import id.divascion.tracerstudy.ui.quiz.QuizMenuView
import id.divascion.tracerstudy.ui.quiz.fragment.AlumniBiodataFragment
import id.divascion.tracerstudy.ui.quiz.fragment.AlumniFiveFragment
import id.divascion.tracerstudy.ui.quiz.fragment.AlumniFourFragment
import id.divascion.tracerstudy.ui.quiz.fragment.AlumniSixFragment
import kotlinx.android.synthetic.main.activity_data_alumni_detail.*
import org.jetbrains.anko.toast

@Suppress("SameParameterValue")
class DataAlumniDetailActivity : AppCompatActivity(), QuizMenuView,
    AlumniFourFragment.OnFragmentInteractionListener,
    AlumniFiveFragment.OnFragmentInteractionListener,
    AlumniSixFragment.OnFragmentInteractionListener {

    private lateinit var dataOne: AlumniQuiz
    private lateinit var dataTwo: AlumniQuiz
    private lateinit var dataThree: AlumniQuiz
    private lateinit var dataFour: AlumniQuiz
    private lateinit var mDatabase: DatabaseReference
    private lateinit var presenter: PresenterQuiz
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_alumni_detail)
        setSupportActionBar(toolbar_alumni_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil Alumni"
        uid = intent.getStringExtra("UID")
        mDatabase = FirebaseDatabase.getInstance().reference
        presenter = PresenterQuiz(mDatabase)

        toolbar_alumni_detail.setNavigationOnClickListener {
            finish()
        }

        tab_layout_data_alumni.setupWithViewPager(view_pager_data_alumni)

        presenter.getDataAlumni(this, uid)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(
        pager: ViewPager?,
        firstTitle: String,
        secondTitle: String,
        thirdTitle: String,
        fourthTitle: String,
        firstFragment: Fragment,
        secondFragment: Fragment,
        thirdFragment: Fragment,
        fourthFragment: Fragment
    ) {
        val adapter = PagerAdapter(supportFragmentManager)

        adapter.addFragment(firstFragment, firstTitle, "ITEM", dataOne)

        adapter.addFragment(secondFragment, secondTitle, "ITEM", dataTwo)

        adapter.addFragment(thirdFragment, thirdTitle, "ITEM", dataThree)

        adapter.addFragment(fourthFragment, fourthTitle, "ITEM", dataFour)

        pager?.adapter = adapter
    }

    override fun showLoading() {
        data_alumni_detail_loading.visibility = View.VISIBLE
    }

    override fun hideLoading(message: String) {
        data_alumni_detail_loading.visibility = View.GONE
        if (message.isNotEmpty()) {
            toast(message)
        }
    }

    override fun getData(data: AlumniQuiz?) {
        val name = data?.quizOne?.name
        data_alumni_detail_display_name.text = name
        supportActionBar?.title = "Profil $name"
        dataOne = AlumniQuiz(data?.quizOne!!, data.quizTwo, data.quizThree)
        dataTwo = AlumniQuiz(AlumniQuizOne(), AlumniQuizTwo(), AlumniQuizThree(), data.quizFour)
        dataThree = AlumniQuiz(
            AlumniQuizOne(),
            AlumniQuizTwo(),
            AlumniQuizThree(),
            AlumniQuizFour(),
            data.quizFive
        )
        dataFour = AlumniQuiz(
            AlumniQuizOne(),
            AlumniQuizTwo(),
            AlumniQuizThree(),
            AlumniQuizFour(),
            AlumniQuizFive(),
            data.quizSix
        )
        val image = if (data.quizOne?.gender.toString().equals("perempuan", true)) {
            R.drawable.women
        } else {
            R.drawable.men
        }
        Picasso.get().load(image)
            .into(data_alumni_detail_pic)
        setupViewPager(
            view_pager_data_alumni, "Biodata", "Relevansi Pendidikan - Pekerjaan",
            "Pengalaman Pembelajaran", "Indikator",
            AlumniBiodataFragment(),
            AlumniFourFragment(),
            AlumniFiveFragment(),
            AlumniSixFragment()
        )
    }

    override fun getData(data: StakeQuiz?) {
        e("Stake", "data alumni none")
    }

    override fun onFragmentInteraction(data: AlumniQuizFour) {
        e("Four Fragment", "data alumni none")
    }

    override fun onFragmentInteraction(data: AlumniQuizFive) {
        e("Five Fragment", "data alumni none")
    }

    override fun onFragmentInteraction(data: AlumniQuizSix) {
        e("Six Fragment", "data alumni none")
    }
}