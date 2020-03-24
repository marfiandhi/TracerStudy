package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizTwoIsSchool(
    var oneName: String = "",
    var oneCountryCity: String? = null,
    var oneMajor: String? = null,
    var oneLevel: String? = null,
    var oneEntranceGraduate: String? = null,
    var twoName: String = "",
    var twoCountryCity: String? = null,
    var twoMajor: String? = null,
    var twoLevel: String? = null,
    var twoEntranceGraduate: String? = null,
    var reason: String = ""
) : Parcelable