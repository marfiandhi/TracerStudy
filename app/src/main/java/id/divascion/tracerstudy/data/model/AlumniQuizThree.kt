package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizThree(
    var name: String = "",
    var type: String = "",
    var position: String = "",
    var monthStart: Int = -1,
    var yearStart: Int = -1,
    var monthQuit: Int? = null,
    var yearQuit: Int? = null,
    var effort: String = "",
    var originInfoCurrentWork: String = "",
    var currentWorkExpectation: String = "",
    var isSatisfied: String = "",
    var reasonCurrentWork: String = "",
    var incomeAverage: String = "",
    var isMajorRelated: String = "",
    var institutionNeeds: String = "",
    var previousWork: String = "",
    var previousWorkDetail: AlumniQuizThreePreviousWork? = null
) : Parcelable