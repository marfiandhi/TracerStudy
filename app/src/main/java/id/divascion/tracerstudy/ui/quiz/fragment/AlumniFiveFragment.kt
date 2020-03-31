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
import id.divascion.tracerstudy.data.model.AlumniQuizFive
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_alumni_five.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniFiveFragment : Fragment() {

    private lateinit var data: AlumniQuizFive
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
        data = SharedPreferenceManager().getAlumniFive(activity!!, user!!.uid) ?: AlumniQuizFive()
        if (status.equals("done", true)) {
            injectData()
            @Suppress("DEPRECATION")
            alumni_five_save_button.setTextColor(activity!!.resources.getColor(R.color.colorBlackTransparentLighter))
            ViewManipulation().disableEnableControls(false, alumni_five_layout)
        }
        alumni_five_save_button.setOnClickListener {
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
        when (data.classExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_class_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_class_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_class_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_class_four.isChecked = true
            }
        }
        when (data.labExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_lab_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_lab_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_lab_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_lab_four.isChecked = true
            }
        }
        when (data.communityExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_community_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_community_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_community_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_community_four.isChecked = true
            }
        }
        when (data.internshipExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_intern_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_intern_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_intern_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_intern_four.isChecked = true
            }
        }
        when (data.organExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_organ_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_organ_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_organ_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_organ_four.isChecked = true
            }
        }
        when (data.campusSocietyExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_campus_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_campus_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_campus_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_campus_four.isChecked = true
            }
        }
        when (data.independentExperience) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_independent_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_independent_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_independent_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_independent_four.isChecked = true
            }
        }
        when (data.englishDay) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_english_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_english_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_english_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_english_four.isChecked = true
            }
        }
        when (data.workPlaceVisit) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_plane_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_plane_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_plane_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_plane_four.isChecked = true
            }
        }
        when (data.spirituality) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_spirit_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_spirit_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_spirit_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_spirit_four.isChecked = true
            }
        }
        when (data.luck) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_luck_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_luck_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_luck_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_luck_four.isChecked = true
            }
        }
        when (data.guardDuty) {
            resources.getString(R.string.prompt_very_important) -> {
                alumni_five_radio_guard_one.isChecked = true
            }
            resources.getString(R.string.prompt_important) -> {
                alumni_five_radio_guard_two.isChecked = true
            }
            resources.getString(R.string.prompt_less_important) -> {
                alumni_five_radio_guard_three.isChecked = true
            }
            resources.getString(R.string.prompt_not_important) -> {
                alumni_five_radio_guard_four.isChecked = true
            }
        }
    }

    private fun checkAndGetData(): Boolean {
        var valid = true
        if (alumni_five_radio_group_class.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_class.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.classExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_class.findFocus()
        }
        if (alumni_five_radio_group_lab.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_lab.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.labExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_lab.findFocus()
        }
        if (alumni_five_radio_group_community.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_community.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.communityExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_community.findFocus()
        }
        if (alumni_five_radio_group_intern.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_intern.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.internshipExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_intern.findFocus()
        }
        if (alumni_five_radio_group_organ.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_organ.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.organExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_organ.findFocus()
        }
        if (alumni_five_radio_group_campus.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_campus.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.campusSocietyExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_campus.findFocus()
        }
        if (alumni_five_radio_group_independent.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_independent.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.independentExperience = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_independent.findFocus()
        }
        if (alumni_five_radio_group_english.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_english.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.englishDay = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_english.findFocus()
        }
        if (alumni_five_radio_group_plane.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_plane.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.workPlaceVisit = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_plane.findFocus()
        }
        if (alumni_five_radio_group_spirit.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_spirit.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.spirituality = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_spirit.findFocus()
        }
        if (alumni_five_radio_group_luck.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_luck.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.luck = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_luck.findFocus()
        }
        if (alumni_five_radio_group_guard.checkedRadioButtonId != -1) {
            val selectedId = alumni_five_radio_group_guard.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.guardDuty = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_five_radio_group_guard.findFocus()
        }
        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_five, container, false)
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
        fun onFragmentInteraction(data: AlumniQuizFive)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumniFiveFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
