package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.StakeQuizThree
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_stake_three.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class StakeThreeFragment : Fragment() {

    private lateinit var data: StakeQuizThree
    private lateinit var status: String
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
        otherAspect()
        data = SharedPreferenceManager().getStakeThree(activity!!, user!!.uid) ?: StakeQuizThree()
        if (status.equals("done", true)) {
            injectData()
            stake_three_save_button.isFocusable = false
            stake_three_save_button.isClickable = false
            stake_three_save_button.isEnabled = false
            @Suppress("DEPRECATION")
            stake_three_save_button.setTextColor(activity!!.resources.getColor(R.color.colorBlackTransparentLighter))
            ViewManipulation()
                .disableEnableControls(false, stake_three_layout)
        }
        stake_three_save_button.setOnClickListener {
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

    private fun injectData() {
        when (data.aviatorKnowledgeBasedOnMajorExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_aviator_knowledge_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_aviator_knowledge_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_aviator_knowledge_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_aviator_knowledge_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_aviator_knowledge_existing_five.isChecked = true
            }
        }
        when (data.aviatorKnowledgeBasedOnMajorSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_aviator_knowledge_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_aviator_knowledge_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_aviator_knowledge_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_aviator_knowledge_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_aviator_knowledge_suitable_five.isChecked = true
            }
        }

        when (data.workSkillsExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_work_skills_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_work_skills_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_work_skills_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_work_skills_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_work_skills_existing_five.isChecked = true
            }
        }
        when (data.worksSkillsSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_work_skills_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_work_skills_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_work_skills_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_work_skills_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_work_skills_suitable_five.isChecked = true
            }
        }

        when (data.professionalEthicsExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_professional_ethics_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_professional_ethics_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_professional_ethics_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_professional_ethics_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_professional_ethics_existing_five.isChecked = true
            }
        }
        when (data.professionalEthicsSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_professional_ethics_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_professional_ethics_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_professional_ethics_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_professional_ethics_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_professional_ethics_suitable_five.isChecked = true
            }
        }

        when (data.fitnessExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_fitness_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_fitness_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_fitness_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_fitness_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_fitness_existing_five.isChecked = true
            }
        }
        when (data.fitnessSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_fitness_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_fitness_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_fitness_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_fitness_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_fitness_suitable_five.isChecked = true
            }
        }

        when (data.moralExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_moral_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_moral_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_moral_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_moral_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_moral_existing_five.isChecked = true
            }
        }
        when (data.moralSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_moral_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_moral_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_moral_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_moral_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_moral_suitable_five.isChecked = true
            }
        }

        when (data.senseOfManagerialExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_sense_of_managerial_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_sense_of_managerial_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_sense_of_managerial_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_sense_of_managerial_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_sense_of_managerial_existing_five.isChecked = true
            }
        }
        when (data.senseOfManagerialSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_sense_of_managerial_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_sense_of_managerial_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_sense_of_managerial_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_sense_of_managerial_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_sense_of_managerial_suitable_five.isChecked = true
            }
        }

        when (data.senseOfLeadershipExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_sense_of_leadership_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_sense_of_leadership_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_sense_of_leadership_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_sense_of_leadership_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_sense_of_leadership_existing_five.isChecked = true
            }
        }
        when (data.senseOfLeadershipSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_sense_of_leadership_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_sense_of_leadership_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_sense_of_leadership_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_sense_of_leadership_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_sense_of_leadership_suitable_five.isChecked = true
            }
        }

        when (data.communicateExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_communicate_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_communicate_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_communicate_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_communicate_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_communicate_existing_five.isChecked = true
            }
        }
        when (data.communicateSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_communicate_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_communicate_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_communicate_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_communicate_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_communicate_suitable_five.isChecked = true
            }
        }

        when (data.foreignLanguageCommunicateExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_foreign_language_communicate_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_foreign_language_communicate_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_foreign_language_communicate_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_foreign_language_communicate_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_foreign_language_communicate_existing_five.isChecked = true
            }
        }
        when (data.foreignLanguageCommunicateSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_foreign_language_communicate_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_foreign_language_communicate_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_foreign_language_communicate_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_foreign_language_communicate_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_foreign_language_communicate_suitable_five.isChecked = true
            }
        }

        when (data.usingTechnologyExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_using_technology_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_using_technology_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_using_technology_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_using_technology_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_using_technology_existing_five.isChecked = true
            }
        }
        when (data.usingTechnologySuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_using_technology_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_using_technology_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_using_technology_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_using_technology_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_using_technology_suitable_four.isChecked = true
            }
        }

        when (data.selfDevelopmentExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_self_development_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_self_development_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_self_development_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_self_development_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_self_development_existing_five.isChecked = true
            }
        }
        when (data.selfDevelopmentSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_self_development_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_self_development_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_self_development_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_self_development_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_self_development_suitable_five.isChecked = true
            }
        }

        when (data.creativityExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_creativity_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_creativity_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_creativity_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_creativity_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_creativity_existing_five.isChecked = true
            }
        }
        when (data.creativitySuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_creativity_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_creativity_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_creativity_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_creativity_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_creativity_suitable_five.isChecked = true
            }
        }

        when (data.initiativeExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_initiative_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_initiative_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_initiative_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_initiative_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_initiative_existing_five.isChecked = true
            }
        }
        when (data.initiativeSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_initiative_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_initiative_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_initiative_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_initiative_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_initiative_suitable_five.isChecked = true
            }
        }

        when (data.workUnderPressureExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_work_under_pressure_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_work_under_pressure_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_work_under_pressure_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_work_under_pressure_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_work_under_pressure_existing_five.isChecked = true
            }
        }
        when (data.workUnderPressureSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_work_under_pressure_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_work_under_pressure_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_work_under_pressure_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_work_under_pressure_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_work_under_pressure_suitable_five.isChecked = true
            }
        }

        when (data.independenceExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_independence_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_independence_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_independence_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_independence_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_independence_existing_five.isChecked = true
            }
        }
        when (data.independenceSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_independence_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_independence_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_independence_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_independence_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_independence_suitable_five.isChecked = true
            }
        }

        when (data.problemSolvingExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_problem_solving_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_problem_solving_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_problem_solving_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_problem_solving_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_problem_solving_existing_five.isChecked = true
            }
        }
        when (data.problemSolvingSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_problem_solving_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_problem_solving_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_problem_solving_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_problem_solving_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_problem_solving_suitable_five.isChecked = true
            }
        }

        when (data.visionaryExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_visionary_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_visionary_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_visionary_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_visionary_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_visionary_existing_five.isChecked = true
            }
        }
        when (data.visionarySuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_visionary_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_visionary_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_visionary_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_visionary_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_visionary_suitable_five.isChecked = true
            }
        }

        when (data.loyaltyExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_loyalty_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_loyalty_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_loyalty_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_loyalty_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_loyalty_existing_five.isChecked = true
            }
        }
        when (data.loyaltySuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_loyalty_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_loyalty_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_loyalty_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_loyalty_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_loyalty_suitable_five.isChecked = true
            }
        }

        stake_three_et_other_aspect.setText(data.otherAspect.toString())
        when (data.otherAspectExisting) {
            resources.getString(R.string.one) -> {
                stake_three_radio_other_aspect_existing_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_other_aspect_existing_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_other_aspect_existing_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_other_aspect_existing_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_other_aspect_existing_five.isChecked = true
            }
        }
        when (data.otherAspectSuitable) {
            resources.getString(R.string.one) -> {
                stake_three_radio_other_aspect_suitable_one.isChecked = true
            }
            resources.getString(R.string.two) -> {
                stake_three_radio_other_aspect_suitable_two.isChecked = true
            }
            resources.getString(R.string.three) -> {
                stake_three_radio_other_aspect_suitable_three.isChecked = true
            }
            resources.getString(R.string.four) -> {
                stake_three_radio_other_aspect_suitable_four.isChecked = true
            }
            resources.getString(R.string.five) -> {
                stake_three_radio_other_aspect_suitable_five.isChecked = true
            }
        }

        when (data.satisfactionPoltekbangGraduate) {
            resources.getString(R.string.prompt_very_not_satisfied) -> {
                stake_three_radio_satisfaction_one.isChecked = true
            }
            resources.getString(R.string.prompt_less_satisfied) -> {
                stake_three_radio_satisfaction_two.isChecked = true
            }
            resources.getString(R.string.prompt_satisfied_enough) -> {
                stake_three_radio_satisfaction_three.isChecked = true
            }
            resources.getString(R.string.prompt_satisfied) -> {
                stake_three_radio_satisfaction_four.isChecked = true
            }
            resources.getString(R.string.prompt_very_satisfied) -> {
                stake_three_radio_satisfaction_five.isChecked = true
            }
        }
    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        if (stake_three_radio_group_aviator_knowledge_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_aviator_knowledge_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.aviatorKnowledgeBasedOnMajorExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_aviator_knowledge_existing.findFocus()
        }
        if (stake_three_radio_group_aviator_knowledge_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_aviator_knowledge_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.aviatorKnowledgeBasedOnMajorSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_aviator_knowledge_suitable.findFocus()
        }

        if (stake_three_radio_group_work_skills_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_work_skills_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workSkillsExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_work_skills_existing.findFocus()
        }
        if (stake_three_radio_group_work_skills_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_work_skills_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.worksSkillsSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_work_skills_suitable.findFocus()
        }

        if (stake_three_radio_group_professional_ethics_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_professional_ethics_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.professionalEthicsExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_professional_ethics_existing.findFocus()
        }
        if (stake_three_radio_group_professional_ethics_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_professional_ethics_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.professionalEthicsSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_professional_ethics_suitable.findFocus()
        }

        if (stake_three_radio_group_fitness_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_fitness_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.fitnessExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_fitness_existing.findFocus()
        }
        if (stake_three_radio_group_fitness_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_fitness_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.fitnessSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_fitness_suitable.findFocus()
        }

        if (stake_three_radio_group_moral_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_moral_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.moralExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_moral_existing.findFocus()
        }
        if (stake_three_radio_group_moral_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_moral_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.moralSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_moral_suitable.findFocus()
        }

        if (stake_three_radio_group_sense_of_managerial_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_sense_of_managerial_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.senseOfManagerialExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_sense_of_managerial_existing.findFocus()
        }
        if (stake_three_radio_group_sense_of_managerial_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_sense_of_managerial_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.senseOfManagerialSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_sense_of_managerial_suitable.findFocus()
        }

        if (stake_three_radio_group_sense_of_leadership_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_sense_of_leadership_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.senseOfLeadershipExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_sense_of_leadership_existing.findFocus()
        }
        if (stake_three_radio_group_sense_of_leadership_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_sense_of_leadership_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.senseOfLeadershipSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_sense_of_leadership_suitable.findFocus()
        }

        if (stake_three_radio_group_communicate_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_communicate_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.communicateExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_communicate_existing.findFocus()
        }
        if (stake_three_radio_group_communicate_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_communicate_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.communicateSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_communicate_suitable.findFocus()
        }

        if (stake_three_radio_group_foreign_language_communicate_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_foreign_language_communicate_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.foreignLanguageCommunicateExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_foreign_language_communicate_existing.findFocus()
        }
        if (stake_three_radio_group_foreign_language_communicate_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_foreign_language_communicate_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.foreignLanguageCommunicateSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_foreign_language_communicate_suitable.findFocus()
        }

        if (stake_three_radio_group_using_technology_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_using_technology_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.usingTechnologyExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_using_technology_existing.findFocus()
        }
        if (stake_three_radio_group_using_technology_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_using_technology_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.usingTechnologySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_using_technology_suitable.findFocus()
        }

        if (stake_three_radio_group_self_development_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_self_development_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.selfDevelopmentExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_self_development_existing.findFocus()
        }
        if (stake_three_radio_group_self_development_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_self_development_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.selfDevelopmentSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_self_development_suitable.findFocus()
        }

        if (stake_three_radio_group_creativity_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_creativity_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.creativityExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_creativity_existing.findFocus()
        }
        if (stake_three_radio_group_creativity_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_creativity_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.creativitySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_creativity_suitable.findFocus()
        }

        if (stake_three_radio_group_initiative_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_initiative_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.initiativeExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_initiative_existing.findFocus()
        }
        if (stake_three_radio_group_initiative_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_initiative_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.initiativeSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_initiative_suitable.findFocus()
        }

        if (stake_three_radio_group_work_under_pressure_existing.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_work_under_pressure_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workUnderPressureExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_work_under_pressure_existing.findFocus()
        }
        if (stake_three_radio_group_work_under_pressure_suitable.checkedRadioButtonId != -1) {
            val selectedId =
                stake_three_radio_group_work_under_pressure_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workUnderPressureSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_work_under_pressure_suitable.findFocus()
        }

        if (stake_three_radio_group_independence_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_independence_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.independenceExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_independence_existing.findFocus()
        }
        if (stake_three_radio_group_independence_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_independence_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.independenceSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_independence_suitable.findFocus()
        }

        if (stake_three_radio_group_problem_solving_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_problem_solving_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.problemSolvingExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_problem_solving_existing.findFocus()
        }
        if (stake_three_radio_group_problem_solving_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_problem_solving_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.problemSolvingSuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_problem_solving_suitable.findFocus()
        }

        if (stake_three_radio_group_visionary_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_visionary_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.visionaryExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_visionary_existing.findFocus()
        }
        if (stake_three_radio_group_visionary_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_visionary_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.visionarySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_visionary_suitable.findFocus()
        }

        if (stake_three_radio_group_loyalty_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_loyalty_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.loyaltyExisting = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_loyalty_existing.findFocus()
        }
        if (stake_three_radio_group_loyalty_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_loyalty_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.loyaltySuitable = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_loyalty_suitable.findFocus()
        }

        data.otherAspect = stake_three_et_other_aspect.text.toString()

        if (stake_three_radio_group_other_aspect_existing.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_other_aspect_existing.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.otherAspectExisting = "${selectedRadioButton.text}"
        } else {
            if (data.otherAspect.toString().isNotEmpty()) {
                valid = false
                stake_three_radio_group_other_aspect_existing.findFocus()
            }
        }
        if (stake_three_radio_group_other_aspect_suitable.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_other_aspect_suitable.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.otherAspectSuitable = "${selectedRadioButton.text}"
        } else {
            if (data.otherAspect.toString().isNotEmpty()) {
                valid = false
                stake_three_radio_group_other_aspect_suitable.findFocus()
            }
        }

        if (stake_three_radio_group_satisfaction.checkedRadioButtonId != -1) {
            val selectedId = stake_three_radio_group_satisfaction.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.satisfactionPoltekbangGraduate = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_three_radio_group_satisfaction.findFocus()
        }

        return valid
    }

    @Suppress("DEPRECATION")
    private fun otherAspect() {
        stake_three_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorLightGrey))
        stake_three_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorLightGrey))
        ViewManipulation().disableEnableControls(
            false,
            stake_three_radio_group_other_aspect_existing
        )
        ViewManipulation().disableEnableControls(
            false,
            stake_three_radio_group_other_aspect_suitable
        )
        stake_three_et_other_aspect.addTextChangedListener(object : TextWatcher {

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
                    stake_three_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorWhite))
                    stake_three_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorWhite))
                    ViewManipulation().disableEnableControls(
                        true,
                        stake_three_radio_group_other_aspect_existing
                    )
                    ViewManipulation().disableEnableControls(
                        true,
                        stake_three_radio_group_other_aspect_suitable
                    )
                } else {
                    stake_three_radio_other_aspect_existing_one.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_existing_two.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_existing_three.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_existing_four.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_existing_five.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_suitable_one.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_suitable_two.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_suitable_three.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_suitable_four.setTextColor(resources.getColor(R.color.colorLightGrey))
                    stake_three_radio_other_aspect_suitable_five.setTextColor(resources.getColor(R.color.colorLightGrey))
                    ViewManipulation().disableEnableControls(
                        false,
                        stake_three_radio_group_other_aspect_existing
                    )
                    ViewManipulation().disableEnableControls(
                        false,
                        stake_three_radio_group_other_aspect_suitable
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
        return inflater.inflate(R.layout.fragment_stake_three, container, false)
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
        fun onFragmentInteraction(data: StakeQuizThree)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StakeThreeFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
