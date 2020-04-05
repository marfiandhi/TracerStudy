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
import id.divascion.tracerstudy.data.model.StakeQuizOne
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_stake_one.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class StakeOneFragment : Fragment() {

    private lateinit var data: StakeQuizOne
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
        data = SharedPreferenceManager().getStakeOne(activity!!, user!!.uid) ?: StakeQuizOne()
        radio()
        if (status.equals("done", true)) {
            injectData()
            @Suppress("DEPRECATION")
            stake_one_save_button.isFocusable = false
            stake_one_save_button.isClickable = false
            stake_one_save_button.isEnabled = false
            stake_one_save_button.setTextColor(activity!!.resources.getColor(R.color.colorWhiteTransparent))
            ViewManipulation()
                .disableEnableControls(false, stake_one_layout)
        }
        stake_one_save_button.setOnClickListener {
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
        stake_one_radio_group_type_work.setOnCheckedChangeListener { _, checkedId ->
            stake_one_et_type_work_other.visibility =
                if (checkedId == R.id.stake_one_radio_type_work_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    private fun injectData() {
        stake_one_et_name_work.setText(data.nameCompany)
        stake_one_et_address_work.setText(data.addressCompany)
        stake_one_et_phone_work.setText(data.phoneCompany)
        stake_one_et_fax_work.setText(data.faxCompany)
        stake_one_et_email_work.setText(data.emailCompany)

        when (data.typeCompany) {
            "Perusahaan Penerbangan Swasta Dalam Negeri" -> {
                stake_one_radio_type_work_one.isChecked = true
            }
            "Perusahaan Penerbangan Swasta Luar Negeri" -> {
                stake_one_radio_type_work_two.isChecked = true
            }
            "BUMN/Perusahaan Milik Pemerintah" -> {
                stake_one_radio_type_work_three.isChecked = true
            }
            "Lembaga/Instansi Pemerintah" -> {
                stake_one_radio_type_work_four.isChecked = true
            }
            "Lembaga/Instansi Swasta" -> {
                stake_one_radio_type_work_five.isChecked = true
            }
            "TNI/Polri" -> {
                stake_one_radio_type_work_six.isChecked = true
            }
            else -> {
                stake_one_radio_type_work_other.isChecked = true
                stake_one_et_type_work_other.setText(data.typeCompany)
            }
        }

        when (data.numberWorkerCompany) {
            "kurang dari 10 orang" -> {
                stake_one_radio_number_worker_one.isChecked = true
            }
            "10-50 orang" -> {
                stake_one_radio_number_worker_two.isChecked = true
            }
            "50-100 orang" -> {
                stake_one_radio_number_worker_three.isChecked = true
            }
            "lebih 100 orang" -> {
                stake_one_radio_number_worker_four.isChecked = true
            }
        }

        when (data.numberAviatorCompany) {
            "kurang dari 10%" -> {
                stake_one_radio_number_aviator_one.isChecked = true
            }
            "10%-25%" -> {
                stake_one_radio_number_aviator_two.isChecked = true
            }
            "25%-50%" -> {
                stake_one_radio_number_aviator_three.isChecked = true
            }
            "lebih dari 50%" -> {
                stake_one_radio_number_aviator_four.isChecked = true
            }
        }

        stake_one_et_number_poltekbang_aviator.setText(data.numberPoltekbangAviatorCompany.toString())
    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        data.nameCompany = stake_one_et_name_work.text.toString()
        if (data.nameCompany.isEmpty()) {
            valid = false
            stake_one_et_name_work.error = resources.getString(R.string.prompt_alert_required)
        } else {
            stake_one_et_name_work.error = null
        }

        data.addressCompany = stake_one_et_address_work.text.toString()
        if (data.addressCompany.isEmpty()) {
            valid = false
            stake_one_et_address_work.error = resources.getString(R.string.prompt_alert_required)
        } else {
            stake_one_et_address_work.error = null
        }

        data.phoneCompany = stake_one_et_phone_work.text.toString()
        if (data.phoneCompany.isEmpty()) {
            valid = false
            stake_one_et_phone_work.error = resources.getString(R.string.prompt_alert_required)
        } else {
            stake_one_et_phone_work.error = null
        }

        data.faxCompany = stake_one_et_fax_work.text.toString()
        data.emailCompany = stake_one_et_email_work.text.toString()
        if (data.emailCompany.isEmpty()) {
            stake_one_et_email_work.error = resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            stake_one_et_email_work.error = null
        }

        if (stake_one_radio_group_type_work.checkedRadioButtonId != -1) {
            val selectedId = stake_one_radio_group_type_work.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            if (selectedRadioButton != stake_one_radio_type_work_other) {
                data.typeCompany = "${selectedRadioButton.text}"
            } else {
                if (stake_one_et_type_work_other.text.toString().isEmpty()) {
                    stake_one_et_type_work_other.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    stake_one_et_type_work_other.error = null
                    data.typeCompany = "${stake_one_et_type_work_other.text}"
                }
            }
        } else {
            valid = false
            stake_one_radio_group_type_work.findFocus()
        }

        if (stake_one_radio_group_number_worker.checkedRadioButtonId != -1) {
            val selectedId = stake_one_radio_group_number_worker.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.numberWorkerCompany = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_one_radio_group_number_worker.findFocus()
        }

        if (stake_one_radio_group_number_aviator.checkedRadioButtonId != -1) {
            val selectedId = stake_one_radio_group_number_aviator.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.numberAviatorCompany = "${selectedRadioButton.text}"
        } else {
            valid = false
            stake_one_radio_group_number_aviator.findFocus()
        }
        if (stake_one_et_number_poltekbang_aviator.text.toString().isEmpty()) {
            valid = false
            stake_one_et_number_poltekbang_aviator.error =
                resources.getString(R.string.prompt_alert_required)
        } else {
            data.numberPoltekbangAviatorCompany =
                stake_one_et_number_poltekbang_aviator.text.toString().toInt()
            stake_one_et_number_poltekbang_aviator.error = null
        }
        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stake_one, container, false)
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
        fun onFragmentInteraction(data: StakeQuizOne)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StakeOneFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
