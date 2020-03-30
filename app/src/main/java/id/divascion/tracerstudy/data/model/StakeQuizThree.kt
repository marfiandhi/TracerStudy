package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuizThree(
    var aviatorKnowledgeBasedOnMajorExisting: String = "",
    var aviatorKnowledgeBasedOnMajorSuitable: String = "",
    var workSkillsExisting: String = "",
    var worksSkillsSuitable: String = "",
    var professionalEthicsExisting: String ="",
    var professionalEthicsSuitable: String = "",
    var fitnessExisting: String = "",
    var fitnessSuitable: String = "",
    var moralExisting: String = "",
    var moralSuitable: String = "",
    var senseOfManagerialExisting: String = "",
    var senseOfManagerialSuitable: String = "",
    var senseOfLeadershipExisting: String = "",
    var senseOfLeadershipSuitable: String = "",
    var communicateExisting: String = "",
    var communicateSuitable: String = "",
    var foreignLanguageCommunicateExisting: String = "",
    var foreignLanguageCommunicateSuitable: String = "",
    var usingTechnologyExisting: String = "",
    var usingTechnologySuitable: String = "",
    var selfDevelopmentExisting: String = "",
    var selfDevelopmentSuitable: String = "",
    var creativityExisting: String = "",
    var creativitySuitable: String = "",
    var initiativeExisting: String = "",
    var initiativeSuitable: String = "",
    var workUnderPressureExisting: String = "",
    var workUnderPressureSuitable: String = "",
    var independenceExisting: String = "",
    var independenceSuitable: String = "",
    var problemSolvingExisting: String = "",
    var problemSolvingSuitable: String = "",
    var visionaryExisting: String = "",
    var visionarySuitable: String = "",
    var loyaltyExisting: String = "",
    var loyaltySuitable: String = "",
    var otherAspect: String? = null,
    var otherAspectExisting: String? = null,
    var otherAspectSuitable: String? = null,
    var satisfactionPoltekbangGraduate: String = ""
) : Parcelable