package id.divascion.tracerstudy.ui.quiz.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizOne
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import kotlinx.android.synthetic.main.fragment_alumni_one.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import java.util.*

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniOneFragment : Fragment() {

    private lateinit var data: AlumniQuizOne
    private lateinit var status: String
    private var listener: OnFragmentInteractionListener? = null
    private var calendar = Calendar.getInstance()
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.get(ARG_PARAM1) as FirebaseUser?
            status = it.getString(ARG_PARAM2) ?: "none"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = SharedPreferenceManager().getAlumniOne(activity!!, user!!.uid) ?: AlumniQuizOne()

        if (status.equals("done", true)) {
            injectData()
        }

        alumni_one_save_button.setOnClickListener {
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
        datePicker()
    }

    @Suppress("DEPRECATION")
    private fun injectData() {
        alumni_one_et_gender_layout.visibility = View.VISIBLE
        alumni_one_tv_gender.visibility = View.GONE
        alumni_one_radio_group.visibility = View.GONE
        alumni_one_button_date_picker.visibility = View.GONE
        alumni_one_save_button.isClickable = false
        alumni_one_save_button.isFocusable = false
        alumni_one_save_button.isEnabled = false
        alumni_one_save_button.setTextColor(activity!!.resources.getColor(R.color.colorWhiteTransparent))

        alumni_one_et_name.setText(data.name)
        alumni_one_et_name.isEnabled = false
        alumni_one_et_name.isClickable = false
        alumni_one_et_gender.setText(data.gender)
        alumni_one_et_gender.isEnabled = false
        alumni_one_et_gender.isClickable = false
        alumni_one_et_school_name.setText(data.schoolName)
        alumni_one_et_school_name.isEnabled = false
        alumni_one_et_school_name.isClickable = false
        alumni_one_et_school_province.setText(data.schoolProvince)
        alumni_one_et_school_province.isEnabled = false
        alumni_one_et_school_province.isClickable = false
        alumni_one_et_school_city.setText(data.schoolCity)
        alumni_one_et_school_city.isEnabled = false
        alumni_one_et_school_city.isClickable = false
        alumni_one_et_school_postcode.setText(data.schoolPostCode)
        alumni_one_et_school_postcode.isEnabled = false
        alumni_one_et_school_postcode.isClickable = false
        alumni_one_et_born_province.setText(data.bornProvince)
        alumni_one_et_born_province.isEnabled = false
        alumni_one_et_born_province.isClickable = false
        alumni_one_et_born_city.setText(data.bornCity)
        alumni_one_et_born_city.isEnabled = false
        alumni_one_et_born_city.isClickable = false
        alumni_one_et_born_date.setText(data.bornDate)
        alumni_one_et_born_date.isEnabled = false
        alumni_one_et_born_date.isClickable = false
        alumni_one_et_office_address.setText(data.officeAddress)
        alumni_one_et_office_address.isEnabled = false
        alumni_one_et_office_address.isClickable = false
        alumni_one_et_office_phone.setText(data.officePhone)
        alumni_one_et_office_phone.isEnabled = false
        alumni_one_et_office_phone.isClickable = false
        alumni_one_et_office_postcode.setText(data.officePostCode)
        alumni_one_et_office_postcode.isEnabled = false
        alumni_one_et_office_postcode.isClickable = false
        alumni_one_et_address_province.setText(data.addressProvince)
        alumni_one_et_address_province.isEnabled = false
        alumni_one_et_address_province.isClickable = false
        alumni_one_et_address_city.setText(data.addressCity)
        alumni_one_et_address_city.isEnabled = false
        alumni_one_et_address_city.isClickable = false
        alumni_one_et_address_district.setText(data.addressDistrict)
        alumni_one_et_address_district.isEnabled = false
        alumni_one_et_address_district.isClickable = false
        alumni_one_et_address_village.setText(data.addressVillage)
        alumni_one_et_address_village.isEnabled = false
        alumni_one_et_address_village.isClickable = false
        alumni_one_et_address_postcode.setText(data.addressPostCode)
        alumni_one_et_address_postcode.isEnabled = false
        alumni_one_et_address_postcode.isClickable = false
        alumni_one_et_address_street.setText(data.addressStreet)
        alumni_one_et_address_street.isEnabled = false
        alumni_one_et_address_street.isClickable = false
        alumni_one_et_address_phone.setText(data.addressPhone)
        alumni_one_et_address_phone.isEnabled = false
        alumni_one_et_address_phone.isClickable = false
        alumni_one_et_address_hp.setText(data.addressMobilePhone)
        alumni_one_et_address_hp.isEnabled = false
        alumni_one_et_address_hp.isClickable = false
        alumni_one_et_email.setText(data.email)
        alumni_one_et_email.isEnabled = false
        alumni_one_et_email.isClickable = false
        alumni_one_et_fb.setText(data.facebook)
        alumni_one_et_fb.isEnabled = false
        alumni_one_et_fb.isClickable = false
    }

    private fun checkAndGetData(): Boolean {
        var valid = true
        data.name = alumni_one_et_name.text.toString()
        if (data.name.isEmpty()) {
            valid = false
            alumni_one_et_name.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_name.error = null
        }

        if (alumni_one_radio_male.isChecked) {
            data.gender = alumni_one_radio_male.text.toString()
        } else {
            data.gender = alumni_one_radio_female.text.toString()
        }

        data.schoolName = alumni_one_et_school_name.text.toString()

        data.schoolProvince = alumni_one_et_school_province.text.toString()

        data.schoolCity = alumni_one_et_school_city.text.toString()

        data.schoolPostCode = alumni_one_et_school_postcode.text.toString()

        data.bornProvince = alumni_one_et_born_province.text.toString()
        if (data.bornProvince.isEmpty()) {
            valid = false
            alumni_one_et_born_province.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_born_province.error = null
        }

        data.bornCity = alumni_one_et_born_city.text.toString()
        if (data.bornCity.isEmpty()) {
            valid = false
            alumni_one_et_born_city.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_born_city.error = null
        }

        data.bornDate = alumni_one_et_born_date.text.toString()
        if (data.bornDate.isEmpty()) {
            valid = false
            alumni_one_et_born_date.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_born_date.error = null
        }

        data.officeAddress = alumni_one_et_office_address.text.toString()

        data.officePhone = alumni_one_et_office_phone.text.toString()

        data.officePhone = alumni_one_et_office_postcode.text.toString()

        data.addressProvince = alumni_one_et_address_province.text.toString()
        if (data.addressProvince.isEmpty()) {
            valid = false
            alumni_one_et_address_province.error =
                resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_province.error = null
        }

        data.addressCity = alumni_one_et_address_city.text.toString()
        if (data.addressCity.isEmpty()) {
            valid = false
            alumni_one_et_address_city.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_city.error = null
        }

        data.addressDistrict = alumni_one_et_address_district.text.toString()
        if (data.addressDistrict.isEmpty()) {
            valid = false
            alumni_one_et_address_district.error =
                resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_district.error = null
        }

        data.addressVillage = alumni_one_et_address_village.text.toString()
        if (data.addressVillage.isEmpty()) {
            valid = false
            alumni_one_et_address_village.error =
                resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_village.error = null
        }

        data.addressPostCode = alumni_one_et_address_postcode.text.toString()

        data.addressStreet = alumni_one_et_address_street.text.toString()
        if (data.addressStreet.isEmpty()) {
            valid = false
            alumni_one_et_address_street.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_street.error = null
        }

        data.addressPhone = alumni_one_et_address_phone.text.toString()

        data.addressMobilePhone = alumni_one_et_address_hp.text.toString()
        if (data.addressMobilePhone.isEmpty()) {
            valid = false
            alumni_one_et_address_hp.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_address_hp.error = null
        }

        data.email = alumni_one_et_email.text.toString()
        if (data.email.isEmpty()) {
            valid = false
            alumni_one_et_email.error = resources.getString(R.string.prompt_alert_required)
        } else {
            alumni_one_et_email.error = null
        }

        data.facebook = alumni_one_et_fb.text.toString()

        return valid
    }

    private fun datePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        val newDate = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        year -= 17

        alumni_one_button_date_picker.setOnClickListener {
            val dpd = DatePickerDialog(activity!!, dateSetListener, 1990, 12, 31)
            newDate.set(year, newDate.get(Calendar.MONTH), newDate.get(Calendar.DATE))
            dpd.datePicker.maxDate = newDate.timeInMillis
            dpd.show()
        }
    }

    private fun updateDateInView() {
        val year = calendar.get(Calendar.YEAR).toString()
        val month = calendar.get(Calendar.MONTH) + 1
        val date = calendar.get(Calendar.DATE)
        val text = StringManipulation().getDateToday(year, month, date)
        alumni_one_et_born_date.setText(text)
    }

    private fun onButtonPressed(status: String) {
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
        fun onFragmentInteraction(data: AlumniQuizOne)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            AlumniOneFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
