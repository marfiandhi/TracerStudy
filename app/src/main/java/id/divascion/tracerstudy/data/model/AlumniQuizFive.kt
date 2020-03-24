package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizFive(
    var classExperience: String = "",
    var labExperience: String = "",
    var communityExperience: String = "",
    var internshipExperience: String = "",
    var organExperience: String = "",
    var campusSocietyExperience: String = "",
    var independentExperience: String = "",
    var englishDay: String = "",
    var planeVisit: String = "",
    var spirituality: String = "",
    var luck: String = "",
    var guardDuty: String = ""
) : Parcelable