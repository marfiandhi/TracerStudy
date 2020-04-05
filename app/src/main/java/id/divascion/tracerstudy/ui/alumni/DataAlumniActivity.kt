package id.divascion.tracerstudy.ui.alumni

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.adapter.DataAlumniAdapter
import id.divascion.tracerstudy.data.model.AlumniList
import id.divascion.tracerstudy.data.presenter.PresenterQuiz
import kotlinx.android.synthetic.main.activity_data_alumni.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

class DataAlumniActivity : AppCompatActivity(), DataAlumniView {

    private lateinit var presenter: PresenterQuiz
    private lateinit var mDatabase: DatabaseReference
    private lateinit var listNullYear: ArrayList<String>
    private lateinit var adapter: DataAlumniAdapter
    private lateinit var user: FirebaseUser
    private var year = "-"
    private var major = "-"
    private var calendar = Calendar.getInstance()
    private var data: MutableList<AlumniList> = mutableListOf()
    private var searchData: MutableList<AlumniList> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_alumni)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Alumni"
        mDatabase =
            FirebaseDatabase.getInstance().reference.child("quiz").child("alumni").child("answer")
        presenter = PresenterQuiz(mDatabase)
        user = FirebaseAuth.getInstance().currentUser!!
        initUI()
        presenter.getDataAlumni(this)
        @Suppress("DEPRECATION")
        data_alumni_swipe.setColorScheme(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        data_alumni_swipe.setOnRefreshListener {
            data.clear()
            presenter.getDataAlumni(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        adapter()
        adapter = DataAlumniAdapter(this, user.uid, data) { uid: String ->
            startActivity<DataAlumniDetailActivity>(
                "UID" to uid
            )
        }
        data_alumni_rv.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        data_alumni_rv.adapter = adapter
    }

    private fun adapter() {
        val currentYear = calendar.get(Calendar.YEAR)
        listNullYear = ArrayList()
        listNullYear.add("-")
        for (i in 1970..currentYear) {
            listNullYear.add(i.toString())
        }

        val yearNullAdapter =
            ArrayAdapter<String>(this, R.layout.spinner_item, listNullYear)
        yearNullAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        data_alumni_spinner_list_year_entrance.adapter = yearNullAdapter

        val majorNullAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            resources.getStringArray(R.array.list_major)
        )
        majorNullAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        data_alumni_spinner_list_major.adapter = majorNullAdapter

        data_alumni_spinner_list_year_entrance.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("DefaultLocale")
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    year = data_alumni_spinner_list_year_entrance.selectedItem.toString()
                    data.clear()
                    if (year.equals("-", true) && major.equals("-", true)) {
                        data.addAll(searchData)
                    } else {
                        searchData.forEach {
                            try {
                                if ((it.entrance.equals(year, true) || year.equals(
                                        "-",
                                        true
                                    )) && (it.major.equals(major, true) || major.equals("-", true))
                                ) {
                                    data.add(it)
                                }
                            } catch (e: Exception) {
                                Log.e("search", e.message.toString())
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {
                    Log.e("Spinner nothing", arg0.toString())
                }

            }

        data_alumni_spinner_list_major.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                @SuppressLint("DefaultLocale")
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    major = data_alumni_spinner_list_major.selectedItem.toString()
                    data.clear()
                    if (major.equals("-", true) && year.equals("-", true)) {
                        data.addAll(searchData)
                    } else {
                        searchData.forEach {
                            try {
                                if ((it.major.equals(major, true) || major.equals(
                                        "-",
                                        true
                                    )) && (it.entrance.equals(
                                        year,
                                        true
                                    ) || year.equals("-", true)
                                            )
                                ) {
                                    data.add(it)
                                }
                            } catch (e: Exception) {
                                Log.e("search", e.message.toString())
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {
                    Log.e("Spinner nothing", arg0.toString())
                }

            }
    }

    override fun showLoading() {
        data_alumni_swipe.isRefreshing = true
    }

    override fun hideLoading(message: String) {
        data_alumni_swipe.isRefreshing = false
    }

    override fun getData(listAlumni: ArrayList<AlumniList>) {
        searchData.clear()
        searchData.addAll(listAlumni)
        data.addAll(listAlumni)
        adapter.notifyDataSetChanged()
    }
}
