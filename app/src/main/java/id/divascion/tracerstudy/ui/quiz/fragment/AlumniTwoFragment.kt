package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizTwo
import id.divascion.tracerstudy.data.model.AlumniQuizTwoIsSchool
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_alumni_two.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import java.util.*

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniTwoFragment : Fragment() {

    private lateinit var data: AlumniQuizTwo
    private lateinit var status: String
    private lateinit var listYear: ArrayList<String>
    private var user: FirebaseUser? = null
    private var calendar = Calendar.getInstance()
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
        data = SharedPreferenceManager().getAlumniTwo(activity!!, user!!.uid) ?: AlumniQuizTwo()
        adapter()
        radio()
        if (status.equals("done", true)) {
            injectData()
            alumni_two_save_button.isClickable = false
            alumni_two_save_button.isFocusable = false
            alumni_two_save_button.isEnabled = false
            @Suppress("DEPRECATION")
            alumni_two_save_button.setTextColor(activity!!.resources.getColor(R.color.colorWhiteTransparent))
        }
        alumni_two_save_button.setOnClickListener {
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

    private fun adapter() {
        val currentYear = calendar.get(Calendar.YEAR)
        listYear = ArrayList()
        for (i in 1970..currentYear) {
            listYear.add(i.toString())
        }

        val monthGraduateEntrance = ArrayAdapter.createFromResource(
            this.activity!!, R.array.list_month_number, R.layout.spinner_item
        )
        monthGraduateEntrance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_two_spinner_list_month_graduated.adapter = monthGraduateEntrance


        val yearAdapter = ArrayAdapter<String>(this.activity!!, R.layout.spinner_item, listYear)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_two_spinner_list_year_graduate.adapter = yearAdapter
        alumni_two_spinner_list_year_entrance.adapter = yearAdapter
        alumni_two_spinner_list_year_graduate.setSelection(listYear.size - 1)
        alumni_two_spinner_list_year_entrance.setSelection(listYear.size - 1)

        val levelSchoolOne = ArrayAdapter.createFromResource(
            this.activity!!, R.array.list_school_level, R.layout.spinner_item
        )
        levelSchoolOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_two_spinner_school_level_one.adapter = levelSchoolOne
        alumni_two_spinner_school_level_two.adapter = levelSchoolOne
    }

    private fun injectData() {
        ViewManipulation().disableEnableControls(false, alumni_two_layout)
        var selectionYearEntrance = -1
        var selectionYearGraduate = -1
        var selectionMonthGraduate = -1
        for (i in listYear.indices) {
            if (listYear[i] == data.yearEntrance.toString()) {
                selectionYearEntrance = i
            }
            if (listYear[i] == data.yearGraduate.toString()) {
                selectionYearGraduate = i
            }
        }
        val monthArray = activity!!.resources.getStringArray(R.array.list_month_number)
        for (i in monthArray.indices) {
            if (monthArray[i] == data.monthGraduate.toString()) {
                selectionMonthGraduate = i
            }
        }

        alumni_two_spinner_list_year_entrance.setSelection(selectionYearEntrance)
        alumni_two_spinner_list_year_graduate.setSelection(selectionYearGraduate)
        alumni_two_spinner_list_month_graduated.setSelection(selectionMonthGraduate)

        when (data.majorProgram) {
            resources.getString(R.string.major_one) -> {
                alumni_two_radio_major_first.isChecked = true
            }
            resources.getString(R.string.major_two) -> {
                alumni_two_radio_major_second.isChecked = true
            }
            resources.getString(R.string.major_three) -> {
                alumni_two_radio_major_third.isChecked = true
            }
            resources.getString(R.string.major_four) -> {
                alumni_two_radio_major_fourth.isChecked = true
            }
            resources.getString(R.string.major_five) -> {
                alumni_two_radio_major_fifth.isChecked = true
            }
            resources.getString(R.string.major_six) -> {
                alumni_two_radio_major_sixth.isChecked = true
            }
            resources.getString(R.string.major_seven) -> {
                alumni_two_radio_major_seventh.isChecked = true
            }
            resources.getString(R.string.major_eight) -> {
                alumni_two_radio_major_eight.isChecked = true
            }
            resources.getString(R.string.major_nine) -> {
                alumni_two_radio_major_ninth.isChecked = true
            }
        }
        if (data.isOrganization.equals("iya", true)) {
            alumni_two_radio_organ_yes.isChecked = true
        } else {
            alumni_two_radio_organ_no.isChecked = true
            alumni_two_radio_group_organ_no.visibility = View.VISIBLE
            when {
                data.isOrganization.contains(
                    "sibuk",
                    true
                ) -> alumni_two_radio_organ_no_first.isChecked = true
                data.isOrganization.contains(
                    "tidak berminat",
                    true
                ) -> alumni_two_radio_organ_no_first.isChecked = true
                data.isOrganization.contains(
                    "tidak sempat",
                    true
                ) -> alumni_two_radio_organ_no_first.isChecked = true
                data.isOrganization.contains(
                    "tidak cocok dengan",
                    true
                ) -> alumni_two_radio_organ_no_first.isChecked = true
                else -> {
                    alumni_two_radio_organ_no_other.isChecked = true
                    val text = StringManipulation().removePunctuation(data.isOrganization, ',')
                    alumni_two_et_organ_no_other.visibility = View.VISIBLE
                    alumni_two_et_organ_no_other.setText(text)
                }
            }
        }

        if (data.afterGraduate.equals("iya", true)) {
            alumni_two_radio_study_yes.isChecked = true
            alumni_two_layout_school_two.visibility = View.VISIBLE
            alumni_two_layout_school_one.visibility = View.VISIBLE
            alumni_two_et_school_name_one.setText(data.afterGraduateDetail?.oneName)
            alumni_two_et_school_location_one.setText(data.afterGraduateDetail?.oneCountryCity)
            alumni_two_et_school_major_one.setText(data.afterGraduateDetail?.oneMajor)
            alumni_two_et_school_year_one.setText(data.afterGraduateDetail?.oneEntranceGraduate)

            alumni_two_et_school_name_two.setText(data.afterGraduateDetail?.twoName)
            alumni_two_et_school_location_two.setText(data.afterGraduateDetail?.twoCountryCity)
            alumni_two_et_school_major_two.setText(data.afterGraduateDetail?.twoMajor)
            alumni_two_et_school_year_two.setText(data.afterGraduateDetail?.twoEntranceGraduate)
            var selectLevelOne = 0
            var selectLeveTwo = 0
            val levelArray = activity!!.resources.getStringArray(R.array.list_school_level)
            for (i in levelArray.indices) {
                if (levelArray[i] == data.afterGraduateDetail?.oneLevel.toString()) {
                    selectLevelOne = i
                }
                if (levelArray[i] == data.afterGraduateDetail?.twoLevel.toString()) {
                    selectLeveTwo = i
                }
            }
            alumni_two_spinner_school_level_one.setSelection(selectLevelOne)
            alumni_two_spinner_school_level_two.setSelection(selectLeveTwo)
        } else {
            alumni_two_radio_study_no.isChecked = true
        }

        when (data.afterGraduateDetail?.reason) {
            "Mengisi kekosongan menganggur" -> {
                alumni_two_radio_school_reason_one.isChecked = true
            }
            "Meningkatkan kompetensi" -> {
                alumni_two_radio_school_reason_two.isChecked = true
            }
            "Ada kesempatan" -> {
                alumni_two_radio_school_reason_third.isChecked = true
            }
            "Sebagai syarat dalam pekerjaan (di tempat bekerja)" -> {
                alumni_two_radio_school_reason_fourth.isChecked = true
            }
            "Kurang yakin bila hanya di bidang ini saja" -> {
                alumni_two_radio_school_reason_fifth.isChecked = true
            }
            else -> {
                alumni_two_radio_school_reason_other.isChecked = true
                alumni_two_et_school_reason_other.visibility = View.VISIBLE
                alumni_two_et_school_reason_other.setText(data.afterGraduateDetail?.reason)
            }
        }

        when (data.jobExpected) {
            "PNS" -> {
                alumni_two_radio_expect_work_one.isChecked = true
            }
            "TNI/Polri" -> {
                alumni_two_radio_expect_work_two.isChecked = true
            }
            "Karyawan BUMN" -> {
                alumni_two_radio_expect_work_three.isChecked = true
            }
            "Karyawan Swasta Perusahaan Dalam Negeri" -> {
                alumni_two_radio_expect_work_four.isChecked = true
            }
            "Karyawan Swasta Perusahaan Luar Negeri" -> {
                alumni_two_radio_expect_work_five.isChecked = true
            }
            "Tenaga Pendidik pada Lemdik Penerbangan Pemerintah" -> {
                alumni_two_radio_expect_work_six.isChecked = true
            }
            "Tenaga Pendidik pada Lemdik Penerbangan Swasta" -> {
                alumni_two_radio_expect_work_seven.isChecked = true
            }
            "Wiraswasta/Wirausaha" -> {
                alumni_two_radio_expect_work_eight.isChecked = true
            }
            else -> {
                alumni_two_radio_expect_work_other.isChecked = true
                alumni_two_radio_expect_work_other.visibility = View.VISIBLE
                alumni_two_et_expect_work_other.setText(data.jobExpected)
            }
        }

        if (data.placementGraduate.equals("iya", true)) {
            alumni_two_radio_placement_yes.isChecked = true
        } else {
            alumni_two_radio_placement_no.isChecked = true
        }

        if (data.applyJob.equals("iya", true)) {
            alumni_two_radio_apply_yes.isChecked = true
        } else {
            alumni_two_radio_apply_no.isChecked = true
        }

        when (data.timeApplyJob) {
            "Sejak tahun pertama perkuliahan" -> {
                alumni_two_radio_when_apply_one.isChecked = true
            }
            "Di tahun kedua perkuliahan" -> {
                alumni_two_radio_when_apply_two.isChecked = true
            }
            "Di tahun ketiga perkuliahan" -> {
                alumni_two_radio_when_apply_three.isChecked = true
            }
            "Di tahun akhir perkuliahan" -> {
                alumni_two_radio_when_apply_four.isChecked = true
            }
            "Setelah lulus" -> {
                alumni_two_radio_when_apply_five.isChecked = true
            }
        }

        if (data.makeCV.equals("iya", true)) {
            alumni_two_radio_cv_yes.isChecked = true
        } else {
            alumni_two_radio_cv_no.isChecked = true
        }

        when (data.timeMakeCV) {
            "Sejak tahun pertama perkuliahan" -> {
                alumni_two_radio_when_cv_one.isChecked = true
            }
            "Di tahun kedua perkuliahan" -> {
                alumni_two_radio_when_cv_two.isChecked = true
            }
            "Di tahun ketiga perkuliahan" -> {
                alumni_two_radio_when_cv_three.isChecked = true
            }
            "Di tahun akhir perkuliahan" -> {
                alumni_two_radio_when_cv_four.isChecked = true
            }
            "Setelah lulus" -> {
                alumni_two_radio_when_cv_five.isChecked = true
            }
        }

        alumni_two_et_gpi.setText(data.GPI)

        if (data.work.equals("iya", true)) {
            alumni_two_radio_work_yes.isChecked = true
        } else {
            alumni_two_radio_work_no.isChecked = true
        }
    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        data.yearEntrance = alumni_two_spinner_list_year_entrance.selectedItem.toString().toInt()
        data.monthGraduate = alumni_two_spinner_list_month_graduated.selectedItem.toString().toInt()
        data.yearGraduate = alumni_two_spinner_list_year_graduate.selectedItem.toString().toInt()

        if (alumni_two_radio_group_major.checkedRadioButtonId != -1) {
            val selectedId = alumni_two_radio_group_major.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.majorProgram = selectedRadioButton.text.toString()
        } else {
            valid = false
            alumni_two_radio_group_major.findFocus()
        }

        when {
            alumni_two_radio_organ_yes.isChecked -> {
                data.isOrganization = alumni_two_radio_organ_yes.text.toString()
            }
            alumni_two_radio_organ_no.isChecked -> {
                data.isOrganization = alumni_two_radio_organ_no.text.toString()
                if (alumni_two_radio_group_organ_no.checkedRadioButtonId != -1) {
                    val selectedId = alumni_two_radio_group_organ_no.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    if (selectedRadioButton != alumni_two_radio_organ_no_other) {
                        data.isOrganization += ", ${selectedRadioButton.text}"
                    } else {
                        if (alumni_two_et_organ_no_other.text.toString().isEmpty()) {
                            alumni_two_et_organ_no_other.error =
                                resources.getString(R.string.prompt_alert_required)
                            valid = false
                        } else {
                            alumni_two_et_organ_no_other.error = null
                            data.isOrganization += ", ${alumni_two_et_organ_no_other.text}"
                        }
                    }
                } else {
                    valid = false
                    alumni_two_radio_group_organ_no.findFocus()
                }
            }
            !alumni_two_radio_organ_yes.isChecked && !alumni_two_radio_organ_no.isChecked -> {
                valid = false
            }
        }

        when {
            alumni_two_radio_study_yes.isChecked -> {
                data.afterGraduate = alumni_two_radio_study_yes.text.toString()
                data.afterGraduateDetail = AlumniQuizTwoIsSchool()
                data.afterGraduateDetail?.oneName = alumni_two_et_school_name_one.text.toString()
                data.afterGraduateDetail?.oneCountryCity =
                    alumni_two_et_school_location_one.text.toString()
                data.afterGraduateDetail?.oneMajor = alumni_two_et_school_major_one.text.toString()
                data.afterGraduateDetail?.oneLevel =
                    alumni_two_spinner_school_level_one.selectedItem.toString()
                data.afterGraduateDetail?.oneEntranceGraduate =
                    alumni_two_et_school_year_one.text.toString()
                if (data.afterGraduateDetail?.oneName!!.isEmpty()) {
                    alumni_two_et_school_name_one.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_two_et_school_name_one.error = null
                }
                if (data.afterGraduateDetail?.oneCountryCity!!.isEmpty()) {
                    alumni_two_et_school_location_one.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_two_et_school_location_one.error = null
                }
                if (data.afterGraduateDetail?.oneMajor!!.isEmpty()) {
                    alumni_two_et_school_major_one.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_two_et_school_major_one.error = null
                }
                if (data.afterGraduateDetail?.oneEntranceGraduate!!.isEmpty()) {
                    alumni_two_et_school_year_one.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_two_et_school_year_one.error = null
                }
                data.afterGraduateDetail?.twoName = alumni_two_et_school_name_two.text.toString()
                data.afterGraduateDetail?.twoCountryCity =
                    alumni_two_et_school_location_two.text.toString()
                data.afterGraduateDetail?.twoMajor = alumni_two_et_school_major_two.text.toString()
                data.afterGraduateDetail?.twoLevel =
                    alumni_two_spinner_school_level_two.selectedItem.toString()
                data.afterGraduateDetail?.twoEntranceGraduate =
                    alumni_two_et_school_year_two.text.toString()
                if (alumni_two_radio_group_school_two.checkedRadioButtonId != -1) {
                    val selectedId = alumni_two_radio_group_school_two.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    if (selectedRadioButton != alumni_two_radio_school_reason_other) {
                        data.afterGraduateDetail?.reason = "${selectedRadioButton.text}"
                    } else {
                        if (alumni_two_et_school_reason_other.text.toString().isEmpty()) {
                            alumni_two_et_school_reason_other.error =
                                resources.getString(R.string.prompt_alert_required)
                            valid = false
                        } else {
                            alumni_two_et_school_reason_other.error = null
                            data.afterGraduateDetail?.reason =
                                "${alumni_two_et_school_reason_other.text}"
                        }
                    }
                } else {
                    valid = false
                    alumni_two_radio_group_school_two.findFocus()
                }
            }
            alumni_two_radio_study_no.isChecked -> {
                data.afterGraduate = alumni_two_radio_study_no.text.toString()
                data.afterGraduateDetail = null
            }
            !alumni_two_radio_study_no.isChecked && !alumni_two_radio_study_yes.isChecked -> {
                valid = false
                alumni_two_radio_group_study.findFocus()
            }
        }
        if (alumni_two_radio_group_expect_work.checkedRadioButtonId != -1) {
            val selectedId = alumni_two_radio_group_expect_work.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            if (selectedRadioButton != alumni_two_radio_expect_work_other) {
                data.jobExpected = "${selectedRadioButton.text}"
            } else {
                if (alumni_two_et_expect_work_other.text.toString().isEmpty()) {
                    alumni_two_et_expect_work_other.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_two_et_expect_work_other.error = null
                    data.jobExpected = "${alumni_two_et_expect_work_other.text}"
                }
            }
        } else {
            valid = false
            alumni_two_radio_group_expect_work.findFocus()
        }

        if (alumni_two_radio_placement_yes.isChecked) {
            data.placementGraduate = alumni_two_radio_placement_yes.text.toString()
        } else if (alumni_two_radio_placement_no.isChecked) {
            data.placementGraduate = alumni_two_radio_placement_no.text.toString()
        } else if (!alumni_two_radio_placement_yes.isChecked && !alumni_two_radio_placement_no.isChecked) {
            valid = false
        }

        if (alumni_two_radio_apply_yes.isChecked) {
            data.applyJob = alumni_two_radio_apply_yes.text.toString()
        } else if (alumni_two_radio_apply_no.isChecked) {
            data.applyJob = alumni_two_radio_apply_no.text.toString()
        } else if (!alumni_two_radio_apply_yes.isChecked && !alumni_two_radio_apply_no.isChecked) {
            valid = false
        }

        if (alumni_two_radio_group_when_apply.checkedRadioButtonId != -1) {
            val selectedId = alumni_two_radio_group_when_apply.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.timeApplyJob = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_two_radio_group_when_apply.findFocus()
        }

        if (alumni_two_radio_cv_yes.isChecked) {
            data.makeCV = alumni_two_radio_cv_yes.text.toString()
        } else if (alumni_two_radio_cv_no.isChecked) {
            data.makeCV = alumni_two_radio_cv_no.text.toString()
        } else if (!alumni_two_radio_cv_yes.isChecked && !alumni_two_radio_cv_no.isChecked) {
            valid = false
        }

        if (alumni_two_radio_group_when_cv.checkedRadioButtonId != -1) {
            val selectedId = alumni_two_radio_group_when_cv.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.timeMakeCV = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_two_radio_group_when_cv.findFocus()
        }

        data.GPI = alumni_two_et_gpi.text.toString()
        if (data.GPI.isEmpty()) {
            alumni_two_et_gpi.error = resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            val IPK = data.GPI.toDouble()
            if (IPK < 0 || IPK > 4.0 || data.GPI[0] == '.' || data.GPI.contains('-') || data.GPI.contains(
                    ','
                ) || data.GPI.contains(
                    ' '
                )
            ) {
                alumni_two_et_gpi.error = "IPK tidak sesuai"
                valid = false
            } else {
                alumni_two_et_gpi.error = null
            }
        }


        if (alumni_two_radio_work_yes.isChecked) {
            data.work = alumni_two_radio_work_yes.text.toString()
        } else if (alumni_two_radio_work_no.isChecked) {
            data.work = alumni_two_radio_work_no.text.toString()
        } else if (!alumni_two_radio_work_yes.isChecked && !alumni_two_radio_work_no.isChecked) {
            valid = false
        }

        return valid
    }

    private fun radio() {
        alumni_two_radio_group_organ.setOnCheckedChangeListener { _, checkedId ->
            alumni_two_radio_group_organ_no.visibility =
                if (checkedId == R.id.alumni_two_radio_organ_no) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_two_radio_group_organ_no.setOnCheckedChangeListener { _, checkedId ->
            alumni_two_et_organ_no_other.visibility =
                if (checkedId == R.id.alumni_two_radio_organ_no_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_two_radio_group_study.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.alumni_two_radio_study_yes) {
                alumni_two_layout_school_one.visibility = View.VISIBLE
                alumni_two_layout_school_two.visibility = View.VISIBLE
            } else {
                alumni_two_layout_school_one.visibility = View.GONE
                alumni_two_layout_school_two.visibility = View.GONE
            }
        }
        alumni_two_radio_group_school_two.setOnCheckedChangeListener { _, checkedId ->
            alumni_two_et_school_reason_other.visibility =
                if (checkedId == R.id.alumni_two_radio_school_reason_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_two_radio_group_expect_work.setOnCheckedChangeListener { _, checkedId ->
            alumni_two_et_expect_work_other.visibility =
                if (checkedId == R.id.alumni_two_radio_expect_work_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_two, container, false)
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
        fun onFragmentInteraction(data: AlumniQuizTwo)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            AlumniTwoFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
