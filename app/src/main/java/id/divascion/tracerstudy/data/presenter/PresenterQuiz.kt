package id.divascion.tracerstudy.data.presenter

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import id.divascion.tracerstudy.data.model.AlumniList
import id.divascion.tracerstudy.data.model.AlumniQuiz
import id.divascion.tracerstudy.data.model.StakeQuiz
import id.divascion.tracerstudy.ui.alumni.DataAlumniView
import id.divascion.tracerstudy.ui.main.MainView
import id.divascion.tracerstudy.ui.quiz.QuizMenuView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PresenterQuiz(private val mDatabase: DatabaseReference) {

    fun addData(view: QuizMenuView, data: AlumniQuiz, uid: String) {
        view.showLoading()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading(p0.message)
                return
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("quiz").child("alumni").child("answer").hasChild(uid)) {
                    view.hideLoading("Gagal menyimpan. Sepertinya Anda pernah menyimpan kuis ini sebelumnya")
                    return
                } else {
                    mDatabase.child("quiz").child("alumni").child("answer").child(uid)
                        .setValue(data)
                        .addOnSuccessListener {
                            doAsync {
                                uiThread {
                                    view.hideLoading("Berhasil menyimpan kuisioner")
                                }
                            }
                        }
                        .addOnFailureListener {
                            view.hideLoading(it.message.toString())
                        }
                    return
                }
            }
        })
    }

    fun addData(view: QuizMenuView, data: StakeQuiz, uid: String) {
        view.showLoading()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading(p0.message)
                return
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("quiz").child("stakeholder").child("answer").hasChild(uid)) {
                    view.hideLoading("Gagal menyimpan. Sepertinya Anda pernah menyimpan kuis ini sebelumnya")
                    return
                } else {
                    mDatabase.child("quiz").child("stakeholder").child("answer").child(uid)
                        .setValue(data)
                        .addOnSuccessListener {
                            doAsync {
                                uiThread {
                                    view.hideLoading("Berhasil menyimpan kuisioner")
                                }
                            }
                        }
                        .addOnFailureListener {
                            view.hideLoading(it.message.toString())
                        }
                    return
                }
            }
        })
    }

    fun getDataAlumni(view: QuizMenuView, uid: String) {
        view.showLoading()
        val sUser = mDatabase.child("quiz").child("alumni").child("answer").child(uid)
        sUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading("${p0.message}. ${p0.details}")
                return
            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(AlumniQuiz::class.java)
                view.getData(data)
                view.hideLoading("")
                return
            }

        })
    }

    fun getDataStake(view: QuizMenuView, uid: String) {
        view.showLoading()
        val sUser = mDatabase.child("quiz").child("stakeholder").child("answer").child(uid)
        sUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading("${p0.message}. ${p0.details}")
                return
            }

            override fun onDataChange(p0: DataSnapshot) {
                val data = p0.getValue(StakeQuiz::class.java)
                view.getData(data)
                view.hideLoading("")
                return
            }

        })
    }

    fun getDataStake(view: MainView) {
        view.showLoading()
        val list = ArrayList<StakeQuiz>()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading("${p0.message}. ${p0.details}")
                return
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (p1: DataSnapshot in p0.children) {
                    val stakeholder = p1.getValue(StakeQuiz::class.java) ?: StakeQuiz()
                    list.add(stakeholder)
                }
                view.getData(list)
                view.hideLoading("")
            }

        })
    }

    fun getDataAlumni(view: DataAlumniView) {
        view.showLoading()
        val list = ArrayList<AlumniList>()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading("${p0.message}. ${p0.details}")
                return
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (p1: DataSnapshot in p0.children) {
                    val alumni = p1.getValue(AlumniQuiz::class.java) ?: AlumniQuiz()
                    val id = p1.key
                    val temp = AlumniList(
                        id.toString(),
                        alumni.quizOne?.name.toString(),
                        alumni.quizOne?.gender.toString(),
                        alumni.quizTwo?.majorProgram.toString(),
                        alumni.quizTwo?.yearEntrance.toString(),
                        alumni.quizTwo?.yearGraduate.toString()
                    )
                    list.add(temp)
                }
                view.getData(list)
                view.hideLoading("")
            }

        })
    }

    fun getDataAlumni(view: DataAlumniView, entrance: String, major: String) {
        view.showLoading()
        val list = ArrayList<AlumniList>()
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                view.hideLoading("${p0.message}. ${p0.details}")
                return
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (p1: DataSnapshot in p0.children) {
                    val alumni = p1.getValue(AlumniQuiz::class.java) ?: AlumniQuiz()
                    val id = p1.key
                    val temp = AlumniList(
                        id.toString(),
                        alumni.quizOne?.name.toString(),
                        alumni.quizOne?.gender.toString(),
                        alumni.quizTwo?.majorProgram.toString(),
                        alumni.quizTwo?.yearEntrance.toString(),
                        alumni.quizTwo?.yearGraduate.toString()
                    )
                    if ((temp.entrance.equals(entrance, true) || entrance.equals(
                            "-",
                            true
                        )) && (temp.major.equals(major, true) || major.equals("-", true))
                    ) {
                        list.add(temp)
                    }
                }
                view.getData(list)
                view.hideLoading("")
            }

        })
    }
}