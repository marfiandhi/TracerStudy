package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuiz(
    var stakeQuizOne: StakeQuizOne? = null,
    var stakeQuizTwo: StakeQuizTwo? = null,
    var stakeQuizThree: StakeQuizThree? = null,
    var stakeQuizFour: StakeQuizFour? = null
) : Parcelable