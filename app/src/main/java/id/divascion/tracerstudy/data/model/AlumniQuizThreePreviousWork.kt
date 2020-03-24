package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizThreePreviousWork(
    var numberTimesChangeJobs: Int = -1,
    var reasonChangeJob: String = "",
    var firstJobName: String = "",
    var firstPlacementJob: String = "",
    var monthStart: Int = -1,
    var yearStart: Int = -1,
    var monthQuit: Int = -1,
    var yearQuit: Int = -1,
    var effort: String = "",
    var originInfoCurrentWork: String = "",
    var currentWorkExpectation: String = "",
    var isSatisfied: String = "",
    var reasonCurrentWork: String = "",
    var incomeAverage: String = "",
    var whenStartLookingJob: String = "",
    var numberCompaniesApplied: Int = -1,
    var durationGetFirstJob: String = ""
) : Parcelable