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
import id.divascion.tracerstudy.data.model.StakeQuizFour
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_stake_four.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class StakeFourFragment : Fragment() {

    private lateinit var data: StakeQuizFour
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
        data = SharedPreferenceManager().getStakeFour(activity!!, user!!.uid) ?: StakeQuizFour()
        if (status.equals("done", true)) {
            injectData()
            stake_four_save_button.isFocusable = false
            stake_four_save_button.isClickable = false
            stake_four_save_button.isEnabled = false
            @Suppress("DEPRECATION")
            stake_four_save_button.setTextColor(activity!!.resources.getColor(R.color.colorBlackTransparentLighter))
            ViewManipulation()
                .disableEnableControls(false, stake_four_layout)
        }
        stake_four_save_button.setOnClickListener {
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
        when (data.poltekbangGraduateQuality) {
            resources.getString(R.string.prompt_very_good) -> stake_four_radio_poltekbang_quality_very_good.isChecked =
                true
            resources.getString(R.string.prompt_good) -> stake_four_radio_poltekbang_quality_good.isChecked =
                true
            resources.getString(R.string.prompt_enough_good) -> stake_four_radio_poltekbang_quality_enough.isChecked =
                true
            resources.getString(R.string.prompt_bad) -> stake_four_radio_poltekbang_quality_bad.isChecked =
                true
            resources.getString(R.string.prompt_very_bad) -> stake_four_radio_poltekbang_quality_very_bad.isChecked =
                true
        }
        stake_four_et_number_aviator_needs_expect.setText(data.numberAviatorNeededExpectation.toString())
        stake_four_et_desired_criteria.setText(data.desiredCriteria)
        stake_four_et_suggestion.setText(data.suggestion)
    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        if (stake_four_radio_group_poltekbang_quality.checkedRadioButtonId != -1) {
            val selectedId = stake_four_radio_group_poltekbang_quality.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.poltekbangGraduateQuality = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_four_radio_group_poltekbang_quality.findFocus()
        }

        if (stake_four_et_number_aviator_needs_expect.text.toString().isEmpty()) {
            stake_four_et_number_aviator_needs_expect.error =
                resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            data.numberAviatorNeededExpectation =
                stake_four_et_number_aviator_needs_expect.text.toString().toInt()
            stake_four_et_number_aviator_needs_expect.error = null
        }

        data.desiredCriteria = stake_four_et_desired_criteria.text.toString()

        if (data.desiredCriteria.isEmpty()) {
            stake_four_et_desired_criteria.error =
                resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            stake_four_et_desired_criteria.error = null
        }

        data.suggestion = stake_four_et_suggestion.text.toString()

        if (data.suggestion.isEmpty()) {
            stake_four_et_suggestion.error = resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            stake_four_et_suggestion.error = null
        }

        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stake_four, container, false)
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
        fun onFragmentInteraction(data: StakeQuizFour)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StakeFourFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
