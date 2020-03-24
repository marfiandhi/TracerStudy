package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizTwo(
    var yearEntrance: String = "",
    var monthGraduate: Int = -1,
    var yearGraduate: Int = -1,
    var majorProgram: String = "",
    var isOrganization: String = "",
    var afterGraduate: String = "",
    var afterGraduateDetail: AlumniQuizTwoIsSchool? = null,
    var placementGraduate: String = "",
    var applyJob: String = "",
    var timeApplyJob: String = "",
    var makeCV: String = "",
    var timeMakeCV: String = "",
    var GPI: String = "",
    var work: String = ""
) : Parcelable