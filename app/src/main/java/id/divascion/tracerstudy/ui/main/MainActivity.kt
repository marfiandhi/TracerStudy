package id.divascion.tracerstudy.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.divascion.tracerstudy.data.model.StakeQuiz
import id.divascion.tracerstudy.data.model.User
import id.divascion.tracerstudy.data.presenter.PresenterData
import id.divascion.tracerstudy.ui.alumni.DataAlumniActivity
import id.divascion.tracerstudy.ui.login.LoginActivity
import id.divascion.tracerstudy.ui.quiz.QuizMenuActivity
import id.divascion.tracerstudy.util.ExportStakeholder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_role.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var user: FirebaseUser
    private lateinit var mRole: String
    private lateinit var userDatabase: User
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var list: MutableList<StakeQuiz> = mutableListOf()
    private var isStakeholder = false
    private var roleConfirm = false
    private var isAlumni = false
    private var loading = false
    private var pause = false
    private var count = 0

    private val timer = object : CountDownTimer(2700, 1) {
        private var run = true
        override fun onTick(millisUntilFinished: Long) {
            run = true
        }

        override fun onFinish() {
            count = 0
            run = false
        }

        fun isRun(): Boolean {
            return run
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(id.divascion.tracerstudy.R.layout.activity_main)
        user = auth.currentUser!!
        val mDatabase = FirebaseDatabase.getInstance().reference
        val presenter = PresenterData(mDatabase)
        presenter.getUser(this, user.uid)
    }

    private fun initUI() {
        if (mRole == "admin") {
            main_admin_button.visibility = View.VISIBLE
        } else {
            main_admin_button.visibility = View.GONE
        }
        popup()
        main_data_button.setOnClickListener {
            startActivity<DataAlumniActivity>()
        }
        main_quiz_button.setOnClickListener {
            if (mRole.isEmpty() || mRole == "none") {
                main_popup.visibility = View.VISIBLE
                main_logout_button.isEnabled = false
                main_data_button.isClickable = false
                main_quiz_button.isEnabled = false
                roleConfirm = true
            } else {
                roleConfirm = false
                main_logout_button.isEnabled = true
                main_data_button.isClickable = true
                main_quiz_button.isEnabled = true
                toQuiz(this.mRole)
            }
        }
        main_logout_button.setOnClickListener {
            alert("Apakah Anda yakin ingin keluar?") {
                positiveButton("Iya") {
                    showLoading()
                    FirebaseAuth.getInstance().signOut()
                    hideLoading("")
                    finishAffinity()
                    startActivity<LoginActivity>()
                }
                negativeButton("Tidak") { it.cancel() }
            }.show()
        }
        main_admin_button.setOnClickListener {
            alert("Anda ingin mengekspor data stakeholder?") {
                positiveButton("Iya") {
                    exportStakeHolder()
                }
                negativeButton("Tidak") { it.cancel() }
            }.show()
        }
        /*
        main_dev_button.setOnClickListener {
            if (mRole != "admin") {
                changeRole("none")
                user = auth.currentUser!!
                mRole = ""
            } else {
                toast("Anda adalah ADMIN. Tidak perlu mengubah ROLE")
            }
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val mDatabase =
                FirebaseDatabase.getInstance().reference.child("quiz").child("stakeholder")
                    .child("answer")
            val presenter = PresenterData(mDatabase)
            presenter.getDataStake(this)
        }

    }

    private fun exportStakeHolder() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            val mDatabase =
                FirebaseDatabase.getInstance().reference.child("quiz").child("stakeholder")
                    .child("answer")
            val presenter = PresenterData(mDatabase)
            presenter.getDataStake(this)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onPause() {
        pause = true
        super.onPause()
    }

    override fun onResume() {
        if (pause) {
            pause = false
        }
        super.onResume()
    }

    private fun popup() {
        popup_radio_group.setOnCheckedChangeListener { _, _ -> popup_error.visibility = View.GONE }
        popup_confirm_button.setOnClickListener {
            val role: String
            isAlumni = popup_radio_alumni.isChecked
            isStakeholder = popup_radio_stakeholder.isChecked
            if (!isAlumni && !isStakeholder) {
                popup_error.visibility = View.VISIBLE
            } else {
                role = if (isAlumni) {
                    "alumni"
                } else {
                    "stakeholder"
                }
                alert(
                    "Role Anda tidak dapat diubah lagi setelah mengonfirmasi. Lanjutkan?",
                    "Peringatan"
                ) {
                    negativeButton("Batal") {
                        main_logout_button.isEnabled = true
                        main_data_button.isClickable = true
                        main_quiz_button.isEnabled = true
                        it.cancel()
                    }
                    positiveButton("Lanjutkan") {
                        changeRole(role)
                    }
                }.show()
            }
        }
        popup_cancel_button.setOnClickListener {
            popup_error.visibility = View.GONE
            main_popup.visibility = View.GONE
            popup_radio_alumni.isChecked = false
            popup_radio_stakeholder.isChecked = false
            popup_radio_stakeholder.isSelected = false
            popup_radio_alumni.isSelected = false
            main_logout_button.isEnabled = true
            main_data_button.isClickable = true
            main_quiz_button.isEnabled = true
            isAlumni = false
            isStakeholder = false
            roleConfirm = false
        }
    }

    private fun changeRole(role: String) {
        showLoading()
        val mDatabase = FirebaseDatabase.getInstance().reference
        val currentUser = auth.currentUser
        userDatabase = User(currentUser?.email.toString(), role, userDatabase.createAt)
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                hideLoading(p0.message)
                return
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mDatabase.child("user").child(user.uid).setValue(userDatabase)
                    .addOnSuccessListener {
                        hideLoading("")
                        mRole = role
                        toQuiz(role)
                    }
                    .addOnFailureListener {
                        hideLoading("Gagal mengubah role. ${it.message}")
                        Log.e("createUser:failure", it.message.toString())
                    }
            }
        })
    }

    private fun toQuiz(role: String) {
        main_popup.visibility = View.GONE
        if (role == "alumni" || role == "stakeholder") {
            startActivity<QuizMenuActivity>("ROLE" to role)
        } else if (role == "none") {
            toast("Role Anda tidak ada.")
        } else if (role == "admin") {
            toast("Anda adalah ADMIN. Anda tidak perlu mengisi kuesioner")
        } else {
            Log.e("role", "Error $role")
            toast("Maaf terdapat kesalahan terhadap status Anda. Silahkan hubungi Admin")
        }
    }

    override fun onBackPressed() {
        when {
            roleConfirm -> {
                main_popup.visibility = View.GONE
                popup_error.visibility = View.GONE
                roleConfirm = false
            }
            !roleConfirm && !loading -> {
                if (count < 1) {
                    toast("Tekan tombol kembali sekali lagi untuk keluar")
                    timer.start()
                    if (timer.isRun()) {
                        count++
                    }
                } else {
                    finishAffinity()
                }
            }
        }
    }

    override fun getData(list: ArrayList<StakeQuiz>) {
        this.list.clear()
        this.list.addAll(list)
        if (this.list.none()) {
            toast("Tidak ada data Stakeholder")
        } else {
            val export = ExportStakeholder(this)
            export.export(this.list)
        }
    }

    override fun getUser(user: User) {
        userDatabase = user
        mRole = userDatabase.role
        initUI()
    }

    override fun showLoading() {
        main_popup.visibility = View.GONE
        main_loading.visibility = View.VISIBLE
        loading = true
        main_logout_button.isEnabled = false
        main_data_button.isClickable = false
        main_quiz_button.isEnabled = false
    }

    override fun hideLoading(message: String) {
        main_loading.visibility = View.GONE
        loading = false
        main_logout_button.isEnabled = true
        main_data_button.isClickable = true
        main_quiz_button.isEnabled = true
        if (message.isNotEmpty()) {
            toast(message)
        }
    }
}
