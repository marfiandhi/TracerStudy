package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuizFour(
    var poltekbangGraduateQuality: String = "",
    var numberAviatorNeededExpectation: Int = -1,
    var desiredCriteria: String = "",
    var suggestion: String = ""
) : Parcelable