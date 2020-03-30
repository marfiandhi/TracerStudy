package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuizTwo(
    var infoDissemination: String = "",
    var companySelection: String = "",
    var isPeriodicRecruitment: String = "",
    var recruitmentIntensity: String? = null,
    var majorSuitabilityExisting: String = "",
    var majorSuitabilitySuitable: String = "",
    var attitudeExisting: String = "",
    var attitudeSuitable: String = "",
    var achievementExisting: String = "",
    var achievementSuitable: String = "",
    var practicalInCampusExisting: String = "",
    var practicalInCampusSuitable: String = "",
    var practicalOutCampusExisting: String = "",
    var practicalOutCampusSuitable: String = "",
    var campusFameExisting: String = "",
    var campusFameSuitable: String = "",
    var workExperienceExisting: String = "",
    var workExperienceSuitable: String = "",
    var foreignLanguageExisting: String = "",
    var foreignLanguageSuitable: String = "",
    var computerExisting: String = "",
    var computerSuitable: String = "",
    var recommendationThirdPartyExisting: String = "",
    var recommendThirdPartySuitable: String = "",
    var testResultExisting: String = "",
    var testResultSuitable: String = "",
    var appearanceInterviewExisting: String = "",
    var appearanceInterviewSuitable: String = "",
    var personalityExisting: String = "",
    var personalitySuitable: String = "",
    var placeOfOriginExisting: String = "",
    var placeOfOriginSuitable: String = "",
    var otherAspect: String? = null,
    var otherAspectExisting: String? = null,
    var otherAspectSuitable: String? = null
) : Parcelable