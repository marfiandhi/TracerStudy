package id.divascion.tracerstudy.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import id.divascion.tracerstudy.ui.login.LoginActivity
import id.divascion.tracerstudy.ui.quiz.QuizMenuActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_role.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var user: FirebaseUser
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var role: String
    private var roleConfirm = false
    private var isAlumni = false
    private var isStakeholder = false
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
        role = user.displayName.toString()
        popup()
        main_data_button.setOnClickListener {
            toast("Data Alumni")
        }
        main_quiz_button.setOnClickListener {
            if(role.isEmpty() || role == "none") {
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
                toQuiz(this.role)
            }
        }
        main_logout_button.setOnClickListener {
            alert("Apakah Anda yakin ingin keluar?") {
                positiveButton("Iya") {
                    showLoading()
                    FirebaseAuth.getInstance().signOut()
                    hideLoading()
                    finishAffinity()
                    startActivity<LoginActivity>()
                }
                negativeButton("Tidak") {it.cancel()}
            }.show()
        }
        //TODO hapus nanti
        main_dev_button.setOnClickListener {
            changeRole("none")
            user = auth.currentUser!!
            role = ""
        }
    }

    override fun onPause() {
        pause = true
        super.onPause()
    }

    override fun onResume() {
        if(pause) {
            user = auth.currentUser!!
            role = user.displayName.toString()
            pause = false
        }
        super.onResume()
    }

    private fun popup() {
        popup_radio_group.setOnCheckedChangeListener { _, _ -> popup_error.visibility = View.GONE }
        popup_confirm_button.setOnClickListener {
            isAlumni = popup_radio_alumni.isChecked
            isStakeholder = popup_radio_stakeholder.isChecked
            if(!isAlumni && !isStakeholder) {
                popup_error.visibility = View.VISIBLE
            } else {
                if(isAlumni) {
                    this.role = "alumni"
                } else {
                    this.role = "stakeholder"
                }
                alert("Role Anda tidak dapat diubah lagi setelah mengonfirmasi. Lanjutkan?", "Peringatan"){
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
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(role)
            .build()
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideLoading()
                    toQuiz(role)
                }
            }
    }

    private fun toQuiz(role: String) {
        main_popup.visibility = View.GONE
        if(role=="alumni" || role=="stakeholder") {
            startActivity<QuizMenuActivity>("ROLE" to role)
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
                if(count<1) {
                    toast("Tekan tombol kembali sekali lagi untuk keluar")
                    timer.start()
                    if(timer.isRun()) {
                        count++
                    }
                } else {
                    finishAffinity()
                }
            }
        }
    }
    private fun showLoading() {
        main_popup.visibility = View.GONE
        main_loading.visibility = View.VISIBLE
        loading = true
        main_logout_button.isEnabled = false
        main_data_button.isClickable = false
        main_quiz_button.isEnabled = false
    }

    private fun hideLoading() {
        main_loading.visibility = View.GONE
        loading = false
        main_logout_button.isEnabled = true
        main_data_button.isClickable = true
        main_quiz_button.isEnabled = true
    }
}
