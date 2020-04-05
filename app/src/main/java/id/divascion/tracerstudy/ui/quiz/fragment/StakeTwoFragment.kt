package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.StakeQuizTwo
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_stake_two.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class StakeTwoFragment : Fragment() {

    private lateinit var data: StakeQuizTwo
    private lateinit var status: String
    private lateinit var infoDisseminationOther: String
    private lateinit var companySelectionOther: String
    private var infoDisseminationList = ArrayList<String>()
    private var companySelectionList = ArrayList<String>()
    private var user: FirebaseUser? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.get(ARG_PARAM1) as FirebaseUser?
            status = it.getString(ARG_PARAM2) ?: "none"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radio()
        otherAspect()
        data = SharedPreferenceManager().getStakeTwo(activity!!, user!!.uid) ?: StakeQuizTwo()
        if (status.equals("done", true)) {
            injectData()
            stake_two_save_button.isFocusable = false
            stake_two_save_button.isClickable = false
            stake_two_save_button.isEnabled = false
            @Suppress("DEPRECATION")
            stake_two_save_button.setTextColor(activity!!.resources.getColor(R.color.colorWhiteTransparent))
            ViewManipulation()
                .disableEnableControls(false, stake_two_layout)
        }
        stake_two_save_button.setOnClickListener {
            alert(
                "Data tidak dapat diubah setelah disimpan.\nPastikan data yang Anda masukkan semuanya telah benar dan sesuai",
                "Peringatan"
            ) {
                isCancelable = false
                positiveButton("Simpan") {
                    if (checkAndGetData()) {
                        onButtonPressed("done")
                    } else {
                        longToast("Tolong lengkapi kuis yang bertanda *")
                        onButtonPressed("miss")
                    }
                }
                negativeButton("Batal") {
                    it.dismiss()
                }
            }.show()
        }
    }

    private fun radio() {
        stake_two_radio_group_period_recruitment.setOnCheckedChangeListener { _, checkedId ->
            stake_two_layout_period_recruitment_yes_extension.visibility =
                if (checkedId == R.id.stake_two_radio_period_recruitment_yes) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
        stake_two_checked_box_company_selection_other.setOnClickListener { v ->
            if (v is CheckBox) {
                stake_two_et_company_selection_other.visibility = if (v.isChecked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
        stake_two_checked_box_info_dissemination_other.setOnClickListener { v ->
            if (v is CheckBox) {
                stake_two_et_info_dissemination_other.visibility = if (v.isChecked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    private fun injectData() {
        infoDisseminationList = StringManipulation()
            .changeToList(data.infoDissemination, ';')

        for (s in infoDisseminationList) {
            when (s) {
                "Iklan di media massa (televisi, radio, internet, dsb)" -> stake_two_checked_box_info_dissemination_one.isChecked =
                    true
                "Pemberitahuan lowongan pekerja untuk kalangan terbatas" -> stake_two_checked_box_info_dissemination_two.isChecked =
                    true
                "Lamaran langsung dari pada lulusan" -> stake_two_checked_box_info_dissemination_three.isChecked =
                    true
                "Menghubungi kampus-kampus terkait" -> stake_two_checked_box_info_dissemination_four.isChecked =
                    true
                "Hubungan pribadi dengan para lulusan" -> stake_two_checked_box_info_dissemination_five.isChecked =
                    true
                else -> {
                    stake_two_checked_box_info_dissemination_other.isChecked = true
                    stake_two_et_info_dissemination_other.visibility = View.VISIBLE
                    stake_two_et_info_dissemination_other.setText(s)
                }
            }
        }
        companySelectionList = StringManipulation()
            .changeToList(data.companySelection, ';')

        for (s in companySelectionList) {
            when (s) {
                "Seleksi sendiri (HRD department atau bagian dalam instansi/perusahaan)" -> stake_two_checked_box_company_selection_one.isChecked =
                    true
                "Kerjasama antara institusi dengan lembaga rekrutmen SDM" -> stake_two_checked_box_company_selection_two.isChecked =
                    true
                "Diserahkan sepenuhnya kepada lembaga rekrutmen SDM" -> stake_two_checked_box_company_selection_three.isChecked =
                    true
                "Menghubungi kampus-kampus terkait untuk rekrutmen SDM" -> stake_two_checked_box_company_selection_four.isChecked =
                    true
                "Beasiswa ikatan dinas" -> stake_two_checked_box_company_selection_five.isChecked =
                    true
                "Sistem magang (program internship)" -> stake_two_checked_box_company_selection_six.isChecked =
                    true
                else -> {
                    stake_two_checked_box_company_selection_other.isChecked = true
                    stake_two_et_company_selection_other.visibility = View.VISIBLE
                    stake_two_et_company_selection_other.setText(s)
                }
            }
        }

        var yes = false
        when (data.isPeriodicRecruitment) {
            resources.getString(R.string.prompt_yes) -> {
                stake_two_radio_period_recruitment_yes.isChecked = true
                yes = true
            }
            resources.getString(R.string.prompt_no) -> {
                stake_two_radio_period_recruitment_no.isChecked = true
            }
        }

        stake_two_layout_yes_extension_gone_result.visibility = View.GONE
        if (yes) {
            stake_two_layout_period_recruitment_yes_extension_result.visibility = View.VISIBLE
            stake_two_period_recruitment_yes_extension_result.text = data.recruitmentIntensity
        } else {
            stake_two_layout_period_recruitment_yes_extension_result.visibility = View.GONE
        }

        when (data.majorSuitabilityExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_suitability_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_suitability_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_suitability_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_suitability_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_suitability_existing_five.isChecked = true
            }
        }
        when (data.majorSuitabilitySuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_suitability_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_suitability_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_suitability_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_suitability_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_suitability_suitable_five.isChecked = true
            }
        }

        when (data.attitudeExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_attitude_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_attitude_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_attitude_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_attitude_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_attitude_existing_five.isChecked = true
            }
        }
        when (data.attitudeSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_attitude_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_attitude_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_attitude_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_attitude_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_attitude_suitable_five.isChecked = true
            }
        }

        when (data.achievementExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_achievement_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_achievement_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_achievement_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_achievement_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_achievement_existing_five.isChecked = true
            }
        }
        when (data.achievementSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_achievement_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_achievement_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_achievement_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_achievement_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_achievement_suitable_five.isChecked = true
            }
        }

        when (data.practicalInCampusExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_practical_in_campus_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_practical_in_campus_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_practical_in_campus_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_practical_in_campus_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_practical_in_campus_existing_five.isChecked = true
            }
        }
        when (data.practicalInCampusSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_practical_in_campus_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_practical_in_campus_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_practical_in_campus_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_practical_in_campus_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_practical_in_campus_suitable_five.isChecked = true
            }
        }

        when (data.practicalOutCampusExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_practical_out_campus_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_practical_out_campus_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_practical_out_campus_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_practical_out_campus_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_practical_out_campus_existing_five.isChecked = true
            }
        }
        when (data.practicalOutCampusSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_practical_out_campus_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_practical_out_campus_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_practical_out_campus_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_practical_out_campus_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_practical_out_campus_suitable_five.isChecked = true
            }
        }

        when (data.campusFameExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_campus_fame_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_campus_fame_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_campus_fame_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_campus_fame_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_campus_fame_existing_five.isChecked = true
            }
        }
        when (data.campusFameSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_campus_fame_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_campus_fame_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_campus_fame_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_campus_fame_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_campus_fame_suitable_five.isChecked = true
            }
        }

        when (data.workExperienceExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_work_experience_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_work_experience_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_work_experience_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_work_experience_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_work_experience_existing_five.isChecked = true
            }
        }
        when (data.workExperienceSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_work_experience_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_work_experience_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_work_experience_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_work_experience_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_work_experience_suitable_five.isChecked = true
            }
        }

        when (data.foreignLanguageExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_foreign_language_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_foreign_language_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_foreign_language_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_foreign_language_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_foreign_language_existing_five.isChecked = true
            }
        }
        when (data.foreignLanguageSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_foreign_language_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_foreign_language_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_foreign_language_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_foreign_language_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_foreign_language_suitable_five.isChecked = true
            }
        }

        when (data.computerExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_computer_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_computer_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_computer_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_computer_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_computer_existing_five.isChecked = true
            }
        }
        when (data.computerSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_computer_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_computer_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_computer_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_computer_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_computer_suitable_five.isChecked = true
            }
        }

        when (data.recommendationThirdPartyExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_recommendation_third_party_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_recommendation_third_party_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_recommendation_third_party_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_recommendation_third_party_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_recommendation_third_party_existing_five.isChecked = true
            }
        }
        when (data.recommendThirdPartySuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_recommendation_third_party_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_recommendation_third_party_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_recommendation_third_party_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_recommendation_third_party_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_recommendation_third_party_suitable_five.isChecked = true
            }
        }

        when (data.testResultExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_test_result_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_test_result_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_test_result_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_test_result_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_test_result_existing_five.isChecked = true
            }
        }
        when (data.testResultSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_test_result_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_test_result_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_test_result_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_test_result_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_test_result_suitable_five.isChecked = true
            }
        }

        when (data.appearanceInterviewExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_appearance_interview_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_appearance_interview_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_appearance_interview_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_appearance_interview_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_appearance_interview_existing_five.isChecked = true
            }
        }
        when (data.appearanceInterviewSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_appearance_interview_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_appearance_interview_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_appearance_interview_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_appearance_interview_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_appearance_interview_suitable_five.isChecked = true
            }
        }

        when (data.personalityExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_personality_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_personality_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_personality_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_personality_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_personality_existing_five.isChecked = true
            }
        }
        when (data.personalitySuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_personality_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_personality_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_personality_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_personality_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_personality_suitable_five.isChecked = true
            }
        }

        when (data.placeOfOriginExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_place_of_origin_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_place_of_origin_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_place_of_origin_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_place_of_origin_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_place_of_origin_existing_five.isChecked = true
            }
        }
        when (data.placeOfOriginSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_place_of_origin_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_place_of_origin_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_place_of_origin_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_place_of_origin_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_place_of_origin_suitable_five.isChecked = true
            }
        }

        stake_two_et_other_aspect.setText(data.otherAspect)

        when (data.otherAspectExisting) {
            resources.getString(R.string.one) -> {
                stake_two_radio_other_aspect_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_other_aspect_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_other_aspect_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_other_aspect_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_other_aspect_existing_five.isChecked = true
            }
        }
        when (data.otherAspectSuitable) {
            resources.getString(R.string.one) -> {
                stake_two_radio_other_aspect_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_two_radio_other_aspect_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_two_radio_other_aspect_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_two_radio_other_aspect_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_two_radio_other_aspect_suitable_five.isChecked = true
            }
        }

    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        if (stake_two_checked_box_info_dissemination_one.isChecked) {
            infoDisseminationList.add(stake_two_checked_box_info_dissemination_one.text.toString())
        } else {
            infoDisseminationList.remove(stake_two_checked_box_info_dissemination_one.text.toString())
        }
        if (stake_two_checked_box_info_dissemination_two.isChecked) {
            infoDisseminationList.add(stake_two_checked_box_info_dissemination_two.text.toString())
        } else {
            infoDisseminationList.remove(stake_two_checked_box_info_dissemination_two.text.toString())
        }
        if (stake_two_checked_box_info_dissemination_three.isChecked) {
            infoDisseminationList.add(stake_two_checked_box_info_dissemination_three.text.toString())
        } else {
            infoDisseminationList.remove(stake_two_checked_box_info_dissemination_three.text.toString())
        }
        if (stake_two_checked_box_info_dissemination_four.isChecked) {
            infoDisseminationList.add(stake_two_checked_box_info_dissemination_four.text.toString())
        } else {
            infoDisseminationList.remove(stake_two_checked_box_info_dissemination_four.text.toString())
        }
        if (stake_two_checked_box_info_dissemination_five.isChecked) {
            infoDisseminationList.add(stake_two_checked_box_info_dissemination_five.text.toString())
        } else {
            infoDisseminationList.remove(stake_two_checked_box_info_dissemination_five.text.toString())
        }
        if (stake_two_checked_box_info_dissemination_other.isChecked) {
            if (stake_two_et_info_dissemination_other.text.toString().isEmpty()) {
                valid = false
                stake_two_et_info_dissemination_other.error =
                    resources.getString(R.string.prompt_alert_required)
            } else {
                stake_two_et_info_dissemination_other.error = null
                infoDisseminationOther = stake_two_et_info_dissemination_other.text.toString()
                infoDisseminationList.add(infoDisseminationOther)
            }
        } else {
            infoDisseminationList.remove(infoDisseminationOther)
            infoDisseminationOther = ""
        }
        if (infoDisseminationList.size == 0) {
            valid = false
        } else {
            for (i in infoDisseminationList.indices) {
                data.infoDissemination += infoDisseminationList[i]
                if (infoDisseminationList.size - 1 != i) {
                    data.infoDissemination += "; "
                }
            }
        }

        if (stake_two_checked_box_company_selection_one.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_one.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_one.text.toString())
        }
        if (stake_two_checked_box_company_selection_two.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_two.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_two.text.toString())
        }
        if (stake_two_checked_box_company_selection_three.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_three.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_three.text.toString())
        }
        if (stake_two_checked_box_company_selection_four.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_four.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_four.text.toString())
        }
        if (stake_two_checked_box_company_selection_five.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_five.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_five.text.toString())
        }
        if (stake_two_checked_box_company_selection_six.isChecked) {
            companySelectionList.add(stake_two_checked_box_company_selection_six.text.toString())
        } else {
            companySelectionList.remove(stake_two_checked_box_company_selection_six.text.toString())
        }
        if (stake_two_checked_box_company_selection_other.isChecked) {
            if (stake_two_et_company_selection_other.text.toString().isEmpty()) {
                valid = false
                stake_two_et_company_selection_other.error =
                    resources.getString(R.string.prompt_alert_required)
            } else {
                stake_two_et_company_selection_other.error = null
                companySelectionOther = stake_two_et_company_selection_other.text.toString()
                companySelectionList.add(companySelectionOther)
            }
        } else {
            companySelectionList.remove(companySelectionOther)
            companySelectionOther = ""
        }
        if (companySelectionList.size == 0) {
            valid = false
        } else {
            for (i in companySelectionList.indices) {
                data.companySelection += companySelectionList[i]
                if (companySelectionList.size - 1 != i) {
                    data.companySelection += "; "
                }
            }
        }

        if (stake_two_radio_group_period_recruitment.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_period_recruitment.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.isPeriodicRecruitment = "${selectedRadioButton.text}"
            if (data.isPeriodicRecruitment == resources.getString(R.string.prompt_yes)) {
                var one = true
                var two = true
                if (stake_two_et_recruitment_intensity_applied.text.isEmpty()) {
                    stake_two_et_recruitment_intensity_applied.error =
                        resources.getString(R.string.prompt_alert_required)
                    one = false
                } else {
                    stake_two_et_recruitment_intensity_applied.error = null
                }
                if (stake_two_et_recruitment_intensity_year.text.isEmpty()) {
                    stake_two_et_recruitment_intensity_year.error =
                        resources.getString(R.string.prompt_alert_required)
                    two = false
                } else {
                    stake_two_et_recruitment_intensity_year.error = null
                }
                if (one && two) {
                    data.recruitmentIntensity =
                        "${stake_two_et_recruitment_intensity_applied.text} kali dalam ${stake_two_et_recruitment_intensity_year.text} tahun"
                } else {
                    valid = false
                }
            }
        } else {
            valid = false
            stake_two_radio_group_period_recruitment.findFocus()
        }

        if (stake_two_radio_group_suitability_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_suitability_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.majorSuitabilityExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_suitability_existing.findFocus()
        }
        if (stake_two_radio_group_suitability_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_suitability_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.majorSuitabilitySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_suitability_suitable.findFocus()
        }

        if (stake_two_radio_group_attitude_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_attitude_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.attitudeExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_attitude_existing.findFocus()
        }
        if (stake_two_radio_group_attitude_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_attitude_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.attitudeSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_attitude_suitable.findFocus()
        }

        if (stake_two_radio_group_achievement_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_achievement_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.achievementExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_achievement_existing.findFocus()
        }
        if (stake_two_radio_group_achievement_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_achievement_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.achievementSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_achievement_suitable.findFocus()
        }

        if (stake_two_radio_group_practical_in_campus_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_practical_in_campus_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.practicalInCampusExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_practical_in_campus_existing.findFocus()
        }
        if (stake_two_radio_group_practical_in_campus_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_practical_in_campus_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.practicalInCampusSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_practical_in_campus_suitable.findFocus()
        }

        if (stake_two_radio_group_practical_out_campus_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_practical_out_campus_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.practicalOutCampusExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_practical_out_campus_existing.findFocus()
        }
        if (stake_two_radio_group_practical_out_campus_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_practical_out_campus_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.practicalOutCampusSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_practical_out_campus_suitable.findFocus()
        }

        if (stake_two_radio_group_campus_fame_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_campus_fame_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.campusFameExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_campus_fame_existing.findFocus()
        }
        if (stake_two_radio_group_campus_fame_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_campus_fame_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.campusFameSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_campus_fame_suitable.findFocus()
        }

        if (stake_two_radio_group_work_experience_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_work_experience_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workExperienceExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_work_experience_existing.findFocus()
        }
        if (stake_two_radio_group_work_experience_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_work_experience_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workExperienceSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_work_experience_suitable.findFocus()
        }

        if (stake_two_radio_group_foreign_language_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_foreign_language_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.foreignLanguageExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_foreign_language_existing.findFocus()
        }
        if (stake_two_radio_group_foreign_language_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_foreign_language_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.foreignLanguageSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_foreign_language_suitable.findFocus()
        }

        if (stake_two_radio_group_computer_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_computer_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.computerExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_computer_existing.findFocus()
        }
        if (stake_two_radio_group_computer_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_computer_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.computerSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_computer_suitable.findFocus()
        }

        if (stake_two_radio_group_recommendation_third_party_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_recommendation_third_party_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.recommendationThirdPartyExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_recommendation_third_party_existing.findFocus()
        }
        if (stake_two_radio_group_recommendation_third_party_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_recommendation_third_party_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.recommendThirdPartySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_recommendation_third_party_suitable.findFocus()
        }

        if (stake_two_radio_group_test_result_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_test_result_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.testResultExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_test_result_existing.findFocus()
        }
        if (stake_two_radio_group_test_result_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_test_result_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.testResultSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_test_result_suitable.findFocus()
        }

        if (stake_two_radio_group_appearance_interview_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_appearance_interview_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.appearanceInterviewExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_appearance_interview_existing.findFocus()
        }
        if (stake_two_radio_group_appearance_interview_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_two_radio_group_appearance_interview_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.appearanceInterviewSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_appearance_interview_suitable.findFocus()
        }

        if (stake_two_radio_group_personality_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_personality_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.personalityExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_personality_existing.findFocus()
        }
        if (stake_two_radio_group_personality_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_personality_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.personalitySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_personality_suitable.findFocus()
        }

        if (stake_two_radio_group_place_of_origin_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_place_of_origin_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.placeOfOriginExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_place_of_origin_existing.findFocus()
        }
        if (stake_two_radio_group_place_of_origin_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_two_radio_group_place_of_origin_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.placeOfOriginSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_two_radio_group_place_of_origin_suitable.findFocus()
        }

        data.otherAspect = stake_two_et_other_aspect.text.toString()

        if (data.otherAspect.toString().isNotEmpty()) {
            if (stake_two_radio_group_other_aspect_existing.checkedRadioButtonId != -1) {
                val selectedId = stake_two_radio_group_other_aspect_existing.checkedRadioButtonId
                val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                data.otherAspectExisting = "${selectedRadioButton.text}"
            } else {
                valid = false
                stake_two_radio_group_other_aspect_existing.findFocus()
            }
            if (stake_two_radio_group_other_aspect_suitable.checkedRadioButtonId != -1) {
                val selectedId = stake_two_radio_group_other_aspect_suitable.checkedRadioButtonId
                val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                data.otherAspectSuitable = "${selectedRadioButton.text}"
            } else {
                valid = false
                stake_two_radio_group_other_aspect_suitable.findFocus()
            }
        }

        return valid
    }

    @Suppress("DEPRECATION")
    private fun otherAspect() {
        stake_two_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_two_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorLightGrey))
        ViewManipulation().disableEnableControls(
            false,
            stake_two_radio_group_other_aspect_existing
        )
        ViewManipulation().disableEnableControls(
            false,
            stake_two_radio_group_other_aspect_suitable
        )
        stake_two_et_other_aspect.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) {
                    stake_two_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_two_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorWhite))
                    ViewManipulation().disableEnableControls(
                        true,
                        stake_two_radio_group_other_aspect_existing
                    )
                    ViewManipulation().disableEnableControls(
                        true,
                        stake_two_radio_group_other_aspect_suitable
                    )
                } else {
                    stake_two_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_two_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorLightGrey))
                    ViewManipulation().disableEnableControls(
                        false,
                        stake_two_radio_group_other_aspect_existing
                    )
                    ViewManipulation().disableEnableControls(
                        false,
                        stake_two_radio_group_other_aspect_suitable
                    )
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stake_two, container, false)
    }

    fun onButtonPressed(status: String) {
        if (status == "done") {
            listener?.onFragmentInteraction(data)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(data: StakeQuizTwo)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StakeTwoFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
