package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuiz(
    var quizOne: AlumniQuizOne = AlumniQuizOne(),
    var quizTwo: AlumniQuizTwo = AlumniQuizTwo(),
    var quizThree: AlumniQuizThree = AlumniQuizThree(),
    var quizFour: AlumniQuizFour = AlumniQuizFour(),
    var quizFive: AlumniQuizFive = AlumniQuizFive(),
    var quizSix: AlumniQuizSix = AlumniQuizSix()
) : Parcelable