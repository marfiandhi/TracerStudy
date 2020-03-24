package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizSix(
    var compete: String = "",
    var graduatesNeeded: String = "",
    var masteringCompetence: AlumniQuizSixCompetence = AlumniQuizSixCompetence(),
    var requiredCompetences: AlumniQuizSixCompetence = AlumniQuizSixCompetence()
) : Parcelable