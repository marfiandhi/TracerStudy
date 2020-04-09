package id.divascion.tracerstudy.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.firebase.auth.*
import com.google.firebase.database.*
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.User
import id.divascion.tracerstudy.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading_screen.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private var loading = false

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(intentFor<MainActivity>("user" to currentUser))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        supportActionBar?.title = getString(R.string.prompt_login)
        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {
            getFormInfo()
            if (checkNullForm()) {
                inputManager.hideSoftInputFromWindow(
                    login_password.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                inputManager.hideSoftInputFromWindow(
                    login_username.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                signIn()
            }
        }
        login_password.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getFormInfo()
                    if (checkNullForm()) {
                        inputManager.hideSoftInputFromWindow(
                            login_password.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                        signIn()
                    }
                    return true
                }
                return false
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun signIn() {
        showLoading()
        loading_text.text = "Login..."
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user!!.displayName.isNullOrEmpty()) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName("none")
                            .build()
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { taskUpdate ->
                                if (taskUpdate.isSuccessful) {
                                    hideLoading()
                                    updateUI(user)
                                } else {
                                    Log.e("createUser:failure", taskUpdate.exception.toString())
                                    toast(getString(R.string.sign_up_failed))
                                    updateUI(null)
                                }
                            }
                    } else {
                        updateUI(user)
                    }
                } else {
                    hideLoading()
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        toast(getString(R.string.invalid_username_password))
                        updateUI(null)
                    } catch (e: FirebaseAuthInvalidUserException) {
                        if (task.exception!!.message!!.contains("no user record", true)) {
                            alert {
                                message =
                                    "Akun dengan email $email belum terdaftar. Ingin buat akun?\n\n" +
                                            "*Pastikan kembali EMAIL dan PASSWORD (minimal 8 karakter) yang ingin didaftarkan telah sesuai*\n\n" +
                                            "Password Anda saat ini ${password.length} karakter"
                                isCancelable = false
                                negativeButton("Batal") {
                                    it.cancel()
                                }
                                positiveButton("Buat akun") {
                                    if (password.length < 8) {
                                        longToast("Password minimal 8 karakter")
                                        login_password.error = "Minimal 8 karakter"
                                    } else {
                                        login_password.error = null
                                        createAccount()
                                    }
                                }
                            }.show()
                        } else {
                            toast(task.exception!!.message.toString())
                        }
                    } catch (e: Exception) {
                        toast(task.exception!!.message.toString())
                    }
                }
            }
    }

    private fun createAccount() {
        showLoading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { createTask ->
                if (createTask.isSuccessful) {
                    hideLoading()
                    val user = auth.currentUser!!
                    createDatabaseUser(user, user.uid, email)
                } else {
                    hideLoading()
                    Log.e("createUser:failure", createTask.exception.toString())
                    toast(getString(R.string.sign_up_failed))
                    updateUI(null)
                }
            }
    }

    private fun createDatabaseUser(user: FirebaseUser, uid: String, email: String) {
        showLoading()
        val calendar = Calendar.getInstance()
        val currentDay = Date()
        calendar.time = currentDay
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val createAt = "$year-$month-$date-$hour:$minute:$second"
        val role = if (email.equals("tracerpoltekbangjayapura@gmail.com", true)) {
            "admin"
        } else {
            "none"
        }
        val newUser = User(email, role, createAt)
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                hideLoading()
                toast(p0.message)
                return
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("user").hasChild(uid)) {
                    mDatabase.child("user").child(uid).setValue(null)
                        .addOnSuccessListener {
                            hideLoading()
                            deleteUser(user, "Akun telah terdaftarkan sebelumnya")
                        }
                        .addOnFailureListener {
                            hideLoading()
                            updateUI(null)
                            Log.e("createUser:failure", it.message.toString())
                            toast("${it.message}")
                        }
                    return
                } else {
                    mDatabase.child("user").child(uid).setValue(newUser)
                        .addOnSuccessListener {
                            hideLoading()
                            updateUI(user)
                        }
                        .addOnFailureListener {
                            hideLoading()
                            Log.e("createUser:failure", it.message.toString())
                            deleteUser(user, getString(R.string.sign_up_failed))
                        }
                }
            }
        })
    }

    private fun deleteUser(user: FirebaseUser, message: String) {
        showLoading()
        val credential = EmailAuthProvider.getCredential(email, password)
        user.reauthenticate(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.delete().addOnCompleteListener {
                    if (task.isSuccessful) {
                        hideLoading()
                        auth.signOut()
                        updateUI(null)
                        toast(message)
                    }
                }
            }
        }
    }

    private fun getFormInfo() {
        this.email = login_username.text.toString()
        this.password = login_password.text.toString()
    }

    private fun checkNullForm(): Boolean {
        var valid = true
        if (TextUtils.isEmpty(this.email)) {
            login_username.error = getString(R.string.prompt_alert_required)
            valid = false
        } else {
            val email = this.email.toCharArray()
            var charAt = false
            var charDot = false
            var countAt = 0
            var dummy = '_'
            for (index in email.indices) {
                if ((email[index] == '.' && dummy == '.') || email[index] == '`' || email[index] == '~'
                    || email[index] == '!' || email[index] == '#' || email[index] == '$' || email[index] == '%'
                    || email[index] == '^' || email[index] == '&' || email[index] == '*' || email[index] == '('
                    || email[index] == ')' || email[index] == '-' || email[index] == '_' || email[index] == '+'
                    || email[index] == '=' || email[index] == '\\' || email[index] == '|' || email[index] == ']'
                    || email[index] == '}' || email[index] == '[' || email[index] == '{' || email[index] == '\''
                    || email[index] == '"' || email[index] == ';' || email[index] == ':' || email[index] == '?'
                    || email[index] == '/' || email[index] == '>' || email[index] == ',' || email[index] == '<'
                ) {
                    charAt = false
                    charDot = false
                    break
                }
                if (email[index] == '@') {
                    countAt++
                    charAt = when (charAt) {
                        true -> false
                        false -> true
                    }
                }
                if (countAt > 1) {
                    charAt = false
                    break
                }

                if (email[index] == '.' && charAt && countAt == 1) {
                    charDot = true
                } else if (email[index] == '.' && charAt && dummy == '.') {
                    charDot = false
                }
                dummy = email[index]
            }
            if (charAt && charDot) {
                login_username.error = null
            } else {
                login_username.error = getString(R.string.invalid_username)
                valid = false
            }
        }
        if (TextUtils.isEmpty(password)) {
            login_password.error = getString(R.string.prompt_alert_required)
            valid = false
        } else {
            login_password.error = null
        }
        return valid
    }

    override fun onBackPressed() {
        if (!loading) {
            super.onBackPressed()
        }
    }

    private fun showLoading() {
        login_loading.visibility = View.VISIBLE
        loading = true
        login_username.isEnabled = false
        login_username.isClickable = false
        login_password.isEnabled = false
        login_password.isClickable = false
        login_button.isEnabled = false
        login_button.isClickable = false
    }

    private fun hideLoading() {
        login_loading.visibility = View.GONE
        loading = false
        login_username.isEnabled = true
        login_username.isClickable = true
        login_password.isEnabled = true
        login_password.isClickable = true
        login_button.isEnabled = true
        login_button.isClickable = true
    }
}
