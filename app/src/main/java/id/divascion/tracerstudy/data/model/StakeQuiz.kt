package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuiz(
    var stakeQuizOne: StakeQuizOne = StakeQuizOne(),
    var stakeQuizTwo: StakeQuizTwo = StakeQuizTwo(),
    var stakeQuizThree: StakeQuizThree = StakeQuizThree(),
    var stakeQuizFour: StakeQuizFour = StakeQuizFour()
) : Parcelable