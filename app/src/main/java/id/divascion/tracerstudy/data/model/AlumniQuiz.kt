package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuiz(
    var quizOne: AlumniQuizOne? = null,
    var quizTwo: AlumniQuizTwo? = null,
    var quizThree: AlumniQuizThree? = null,
    var quizFour: AlumniQuizFour? = null,
    var quizFive: AlumniQuizFive? = null,
    var quizSix: AlumniQuizSix? = null
) : Parcelable