package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizFour(
    var isUseful: String = "",
    var practicalAdvice: String? = null,
//    var competenceSupportMajor: String = "",
//    var competenceSupportExpert: String = "",
    var requiredCompetencies: String? = null
) : Parcelable