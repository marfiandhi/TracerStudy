package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizSix
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_alumni_six.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniSixFragment : Fragment() {

    private lateinit var data: AlumniQuizSix
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
        data = SharedPreferenceManager().getAlumniSix(activity!!, user!!.uid) ?: AlumniQuizSix()
        if (status.equals("done", true)) {
            injectData()
            @Suppress("DEPRECATION")
            alumni_six_save_button.setTextColor(activity!!.resources.getColor(R.color.colorBlackTransparentLighter))
            ViewManipulation().disableEnableControls(false, alumni_six_layout)
        }
        alumni_six_save_button.setOnClickListener {
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
        when (data.compete) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_compete_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_compete_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_compete_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_compete_four.isChecked = true
            }
        }
        when (data.graduatesNeeded) {
            "Generik Umum" -> {
                alumni_six_radio_graduate_generic.isChecked = true
            }
            "Spesifik" -> {
                alumni_six_radio_graduate_specific.isChecked = true
            }
        }
        when (data.masteringCompetence.generalKnowledge) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_general_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_general_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_general_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_general_four.isChecked = true
            }
        }
        when (data.masteringCompetence.english) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_english_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_english_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_english_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_english_four.isChecked = true
            }
        }
        when (data.masteringCompetence.computer) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_computer_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_computer_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_computer_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_computer_four.isChecked = true
            }
        }
        when (data.masteringCompetence.researchMethodology) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_research_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_research_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_research_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_research_four.isChecked = true
            }
        }
        when (data.masteringCompetence.teamwork) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_teamwork_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_teamwork_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_teamwork_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_teamwork_four.isChecked = true
            }
        }
        when (data.masteringCompetence.communicationVerbal) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_verbal_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_verbal_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_verbal_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_verbal_four.isChecked = true
            }
        }
        when (data.masteringCompetence.communicationWritten) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_written_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_written_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_written_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_written_four.isChecked = true
            }
        }
        when (data.masteringCompetence.communityDevelopment) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_community_development_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_community_development_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_community_development_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_community_development_four.isChecked = true
            }
        }
        when (data.masteringCompetence.theoreticalSpecific) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_theory_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_theory_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_theory_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_theory_four.isChecked = true
            }
        }
        when (data.masteringCompetence.practicallySpecific) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_practical_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_practical_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_practical_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_practical_four.isChecked = true
            }
        }
        when (data.masteringCompetence.organManagement) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_organ_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_organ_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_organ_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_organ_four.isChecked = true
            }
        }
        when (data.masteringCompetence.leadership) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_mastering_leadership_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_mastering_leadership_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_mastering_leadership_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_mastering_leadership_four.isChecked = true
            }
        }
        when (data.requiredCompetences.generalKnowledge) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_general_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_general_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_general_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_general_four.isChecked = true
            }
        }
        when (data.requiredCompetences.english) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_english_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_english_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_english_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_english_four.isChecked = true
            }
        }
        when (data.requiredCompetences.computer) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_computer_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_computer_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_computer_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_computer_four.isChecked = true
            }
        }
        when (data.requiredCompetences.researchMethodology) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_research_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_research_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_research_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_research_four.isChecked = true
            }
        }
        when (data.requiredCompetences.teamwork) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_teamwork_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_teamwork_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_teamwork_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_teamwork_four.isChecked = true
            }
        }
        when (data.requiredCompetences.communicationVerbal) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_verbal_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_verbal_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_verbal_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_verbal_four.isChecked = true
            }
        }
        when (data.requiredCompetences.communicationWritten) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_written_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_written_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_written_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_written_four.isChecked = true
            }
        }
        when (data.requiredCompetences.communityDevelopment) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_community_development_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_community_development_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_community_development_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_community_development_four.isChecked = true
            }
        }
        when (data.requiredCompetences.theoreticalSpecific) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_theory_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_theory_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_theory_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_theory_four.isChecked = true
            }
        }
        when (data.requiredCompetences.practicallySpecific) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_practical_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_practical_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_practical_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_practical_four.isChecked = true
            }
        }
        when (data.requiredCompetences.organManagement) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_organ_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_organ_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_organ_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_organ_four.isChecked = true
            }
        }
        when (data.requiredCompetences.leadership) {
            resources.getString(R.string.prompt_very_capable) -> {
                alumni_six_radio_required_leadership_one.isChecked = true
            }
            resources.getString(R.string.prompt_capable) -> {
                alumni_six_radio_required_leadership_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_capable) -> {
                alumni_six_radio_required_leadership_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_capable) -> {
                alumni_six_radio_required_leadership_four.isChecked = true
            }
        }
    }

    private fun checkAndGetData(): Boolean {
        var valid = true
        if (alumni_six_radio_group_compete.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_compete.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.compete = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_compete.findFocus()
        }
        if (alumni_six_radio_group_graduate.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_graduate.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.graduatesNeeded = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_graduate.findFocus()
        }

        if (alumni_six_radio_group_mastering_general.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_general.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.generalKnowledge = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_general.findFocus()
        }
        if (alumni_six_radio_group_mastering_english.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_english.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.english = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_english.findFocus()
        }
        if (alumni_six_radio_group_mastering_computer.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_computer.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.computer = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_computer.findFocus()
        }
        if (alumni_six_radio_group_mastering_research.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_research.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.researchMethodology = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_research.findFocus()
        }
        if (alumni_six_radio_group_mastering_teamwork.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_teamwork.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.teamwork = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_teamwork.findFocus()
        }
        if (alumni_six_radio_group_mastering_verbal.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_verbal.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.communicationVerbal = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_verbal.findFocus()
        }
        if (alumni_six_radio_group_mastering_written.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_written.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.communicationWritten = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_written.findFocus()
        }
        if (alumni_six_radio_group_mastering_community_development.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_community_development.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.communityDevelopment = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_community_development.findFocus()
        }
        if (alumni_six_radio_group_mastering_theory.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_theory.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.theoreticalSpecific = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_theory.findFocus()
        }
        if (alumni_six_radio_group_mastering_practical.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_practical.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.practicallySpecific = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_practical.findFocus()
        }
        if (alumni_six_radio_group_mastering_organ.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_organ.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.organManagement = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_organ.findFocus()
        }
        if (alumni_six_radio_group_mastering_leadership.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_mastering_leadership.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.masteringCompetence.leadership = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_mastering_leadership.findFocus()
        }

        if (alumni_six_radio_group_required_general.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_general.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.generalKnowledge = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_general.findFocus()
        }
        if (alumni_six_radio_group_required_english.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_english.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.english = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_english.findFocus()
        }
        if (alumni_six_radio_group_required_computer.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_computer.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.computer = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_computer.findFocus()
        }
        if (alumni_six_radio_group_required_research.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_research.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.researchMethodology = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_research.findFocus()
        }
        if (alumni_six_radio_group_required_teamwork.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_teamwork.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.teamwork = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_teamwork.findFocus()
        }
        if (alumni_six_radio_group_required_verbal.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_verbal.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.communicationVerbal = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_verbal.findFocus()
        }
        if (alumni_six_radio_group_required_written.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_written.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.communicationWritten = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_written.findFocus()
        }
        if (alumni_six_radio_group_required_community_development.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_community_development.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.communityDevelopment = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_community_development.findFocus()
        }
        if (alumni_six_radio_group_required_theory.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_theory.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.theoreticalSpecific = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_theory.findFocus()
        }
        if (alumni_six_radio_group_required_practical.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_practical.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.practicallySpecific = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_practical.findFocus()
        }
        if (alumni_six_radio_group_required_organ.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_organ.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.organManagement = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_organ.findFocus()
        }
        if (alumni_six_radio_group_required_leadership.checkedRadioButtonId != -1) {
            val selectedId = alumni_six_radio_group_required_leadership.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.requiredCompetences.leadership = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_six_radio_group_required_leadership.findFocus()
        }
        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_six, container, false)
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
        fun onFragmentInteraction(data: AlumniQuizSix)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumniSixFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
