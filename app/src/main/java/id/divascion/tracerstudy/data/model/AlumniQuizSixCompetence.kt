package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizSixCompetence(
    var generalKnowledge: String = "",
    var english: String = "",
    var computer: String = "",
    var researchMethodology: String = "",
    var teamwork: String = "",
    var communicationVerbal: String = "",
    var communicationWritten: String = "",
    var communityDevelopment: String = "",
    var theoreticalSpecific: String = "",
    var practicallySpecific: String = "",
    var organManagement: String = "",
    var leadership: String = ""
) : Parcelable