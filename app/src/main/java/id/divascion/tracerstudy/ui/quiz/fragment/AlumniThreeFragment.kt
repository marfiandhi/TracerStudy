package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseUser
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizThree
import id.divascion.tracerstudy.data.model.AlumniQuizThreePreviousWork
import id.divascion.tracerstudy.util.SharedPreferenceManager
import id.divascion.tracerstudy.util.StringManipulation
import id.divascion.tracerstudy.util.ViewManipulation
import kotlinx.android.synthetic.main.fragment_alumni_three.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.longToast
import java.util.*
import kotlin.collections.ArrayList

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniThreeFragment : Fragment() {

    private lateinit var data: AlumniQuizThree
    private lateinit var status: String
    private lateinit var originInfoOther: String
    private lateinit var originInfoOtherPrevious: String
    private lateinit var listYear: ArrayList<String>
    private lateinit var listNullYear: ArrayList<String>
    private var originInfo = ArrayList<String>()
    private var originInfoPrevious = ArrayList<String>()
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

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteText =
            "<font color=#FFFFFF>Pertanyaan di bawah ini </font><font color=#FF0000>terkait</font><font color=#FFFFFF> dengan </font><font color=#75FF1A>pekerjaan pertama</font><font color=#FFFFFF>.</font>"
        alumni_three_tv_note.text = Html.fromHtml(noteText)
        data = SharedPreferenceManager().getAlumniThree(activity!!, user!!.uid) ?: AlumniQuizThree()
        radio()
        adapter()
        if (status.equals("done", true)) {
            injectData()
            alumni_three_save_button.isClickable = false
            alumni_three_save_button.isFocusable = false
            alumni_three_save_button.isEnabled = false
            alumni_three_save_button.setTextColor(activity!!.resources.getColor(R.color.colorBlackTransparentLighter))
            ViewManipulation().disableEnableControls(false, alumni_three_layout)
        }
        alumni_three_save_button.setOnClickListener {
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
        alumni_three_radio_group_type_work.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_type_work_other.visibility =
                if (checkedId == R.id.alumni_three_radio_type_work_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        /*alumni_three_radio_group_position.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.alumni_three_radio_position_yes) {
                alumni_three_radio_group_position_yes.visibility = View.VISIBLE
                alumni_three_radio_group_position_no.visibility = View.GONE
            } else {
                alumni_three_radio_group_position_yes.visibility = View.GONE
                alumni_three_radio_group_position_no.visibility = View.VISIBLE
            }
        }*/

        /*alumni_three_radio_group_position_yes.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_position_other.visibility =
                if (checkedId == R.id.alumni_three_radio_position_yes_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }*/

        /*alumni_three_radio_group_position_no.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_position_other.visibility =
                if (checkedId == R.id.alumni_three_radio_position_no_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }*/

        alumni_three_radio_group_reason.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_reason_other.visibility =
                if (checkedId == R.id.alumni_three_radio_reason_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_three_radio_group_previous_work.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_layout_previous_work.visibility =
                if (checkedId == R.id.alumni_three_radio_previous_work_yes) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_three_radio_group_reason_change.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_reason_change_other.visibility =
                if (checkedId == R.id.alumni_three_radio_reason_change_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_three_radio_group_reason_previous.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_reason_previous_other.visibility =
                if (checkedId == R.id.alumni_three_radio_reason_previous_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    private fun adapter() {
        val currentYear = calendar.get(Calendar.YEAR)
        listYear = ArrayList()
        listNullYear = ArrayList()
        listNullYear.add("-")
        for (i in 1970..currentYear) {
            listYear.add(i.toString())
            listNullYear.add(i.toString())
        }

        val monthJob = ArrayAdapter.createFromResource(
            this.activity!!, R.array.list_month_number, R.layout.spinner_item
        )
        monthJob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_three_spinner_list_month_entrance.adapter = monthJob
        alumni_three_spinner_list_month_entrance_previous.adapter = monthJob
        alumni_three_spinner_list_month_stop_previous.adapter = monthJob

        val monthJobNull = ArrayAdapter.createFromResource(
            this.activity!!, R.array.list_month_number_null, R.layout.spinner_item
        )
        monthJobNull.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_three_spinner_list_month_stop.adapter = monthJobNull

        val yearAdapter = ArrayAdapter<String>(this.activity!!, R.layout.spinner_item, listYear)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_three_spinner_list_year_entrance.adapter = yearAdapter
        alumni_three_spinner_list_year_entrance_previous.adapter = yearAdapter
        alumni_three_spinner_list_year_stop_previous.adapter = yearAdapter
        alumni_three_spinner_list_year_entrance.setSelection(listYear.size - 1)
        alumni_three_spinner_list_year_entrance_previous.setSelection(listYear.size - 1)
        alumni_three_spinner_list_year_stop_previous.setSelection(listYear.size - 1)

        val yearNullAdapter =
            ArrayAdapter<String>(this.activity!!, R.layout.spinner_item, listNullYear)
        yearNullAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_three_spinner_list_year_stop.adapter = yearNullAdapter

        val estimatedLookJob = ArrayAdapter.createFromResource(
            this.activity!!, R.array.list_when_looking, R.layout.spinner_item
        )
        estimatedLookJob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alumni_three_spinner_when_look_previous.adapter = estimatedLookJob
    }

    private fun injectData() {
        alumni_three_et_name_work.setText(data.name)

        when (data.type) {
            "Pemerintah (pusat/departemen)" -> {
                alumni_three_radio_type_work_one.isChecked = true
            }
            "Pemerintah (daerah)" -> {
                alumni_three_radio_type_work_two.isChecked = true
            }
            "Pemerintah (BUMN, BHMN)" -> {
                alumni_three_radio_type_work_three.isChecked = true
            }
            "Swasta (jasa)" -> {
                alumni_three_radio_type_work_four.isChecked = true
            }
            "Swasta (manufaktur)" -> {
                alumni_three_radio_type_work_five.isChecked = true
            }
            "Wiraswasta" -> {
                alumni_three_radio_type_work_six.isChecked = true
            }
            "Perusahaan Asing/Luar Negeri" -> {
                alumni_three_radio_type_work_seven.isChecked = true
            }
            "TNI/Polri" -> {
                alumni_three_radio_type_work_eight.isChecked = true
            }
            else -> {
                alumni_three_radio_type_work_other.isChecked = true
                alumni_three_et_type_work_other.setText(data.type)
            }
        }

        var selectionYearStart = -1
        var selectionYearQuit = -1
        var selectionMonthQuit = -1
        var selectionMonthStart = -1
        for (i in listYear.indices) {
            if (listYear[i] == data.yearStart.toString()) {
                selectionYearStart = i
            }
        }
        for (i in listNullYear.indices) {
            if (listNullYear[i] == data.yearQuit.toString()) {
                selectionYearQuit = i
            }
        }

        val monthArray = activity!!.resources.getStringArray(R.array.list_month_number)
        for (i in monthArray.indices) {
            if (monthArray[i] == data.monthStart.toString()) {
                selectionMonthStart = i
            }
        }
        val monthNullArray = activity!!.resources.getStringArray(R.array.list_month_number_null)
        for (i in monthNullArray.indices) {
            if (monthNullArray[i] == data.monthQuit.toString()) {
                selectionMonthQuit = i
            }
        }

        alumni_three_spinner_list_month_entrance.setSelection(selectionMonthStart)
        alumni_three_spinner_list_year_entrance.setSelection(selectionYearStart)
        alumni_three_spinner_list_month_stop.setSelection(selectionMonthQuit)
        alumni_three_spinner_list_year_stop.setSelection(selectionYearQuit)

        when (data.effort) {
            "Aktif (mencari sendiri)" -> {
                alumni_three_radio_effort_active.isChecked = true
            }
            "Pasif (ditawari pekerjaan)" -> {
                alumni_three_radio_effort_passive.isChecked = true
            }
        }

        originInfo = StringManipulation().changeToList(data.originInfoCurrentWork, ';')

        for (s in originInfo) {
            when (s) {
                "Iklan" -> alumni_three_checked_box_origin_info_one.isChecked = true
                "Internet" -> alumni_three_checked_box_origin_info_two.isChecked = true
                "Almamater/alumni" -> alumni_three_checked_box_origin_info_three.isChecked = true
                "Koneksi (teman, dosen, saudara/keluarga, dll)" -> alumni_three_checked_box_origin_info_four.isChecked =
                    true
                "Info lowongan dari kampus" -> alumni_three_checked_box_origin_info_five.isChecked =
                    true
                else -> {
                    alumni_three_checked_box_origin_info_other.isChecked = true
                    alumni_three_et_origin_info_other.visibility = View.VISIBLE
                    alumni_three_et_origin_info_other.setText(s)
                }
            }
        }

        when (data.currentWorkExpectation) {
            "Sangat sesuai dengan harapan" -> {
                alumni_three_radio_expectation_very.isChecked = true
            }
            "Sesuai harapan" -> {
                alumni_three_radio_expectation_yes.isChecked = true
            }
            "Kurang sesuai harapan" -> {
                alumni_three_radio_expectation_less.isChecked = true
            }
            "Tidak sesuai harapan" -> {
                alumni_three_radio_expectation_no.isChecked = true
            }
        }

        when (data.isSatisfied) {
            resources.getString(R.string.prompt_very_satisfied) -> {
                alumni_three_radio_satisfied_very.isChecked = true
            }
            resources.getString(R.string.prompt_satisfied) -> {
                alumni_three_radio_satisfied_yes.isChecked = true
            }
            resources.getString(R.string.prompt_less_satisfied) -> {
                alumni_three_radio_satisfied_less.isChecked = true
            }
            resources.getString(R.string.prompt_not_satisfied) -> {
                alumni_three_radio_satisfied_no.isChecked = true
            }
        }

        when (data.reasonCurrentWork) {
            "Gaji memadai" -> {
                alumni_three_radio_reason_one.isChecked = true
            }
            "Sesuai bidang keilmuan" -> {
                alumni_three_radio_reason_two.isChecked = true
            }
            "Mendapatkan pengalaman" -> {
                alumni_three_radio_reason_three.isChecked = true
            }
            "Mendapatkan ilmu pengetahuan" -> {
                alumni_three_radio_reason_four.isChecked = true
            }
            "Mendapatkan keterampilan" -> {
                alumni_three_radio_reason_five.isChecked = true
            }
            else -> {
                alumni_three_radio_reason_other.isChecked = true
                alumni_three_et_reason_other.setText(data.reasonCurrentWork)
            }
        }

        when (data.incomeAverage) {
            resources.getString(R.string.rp_1_000_000) -> {
                alumni_three_radio_income_one.isChecked = true
            }
            resources.getString(R.string.rp_1_000_000_rp_3_000_000) -> {
                alumni_three_radio_income_two.isChecked = true
            }
            resources.getString(R.string.rp_3_000_000_rp_5_000_000) -> {
                alumni_three_radio_income_three.isChecked = true
            }
            resources.getString(R.string.rp_5_000_000_rp_7_500_000) -> {
                alumni_three_radio_income_four.isChecked = true
            }
            resources.getString(R.string.rp_7_500_000_rp_10_000_000) -> {
                alumni_three_radio_income_five.isChecked = true
            }
            resources.getString(R.string.rp_10_000_000_rp_12_500_000) -> {
                alumni_three_radio_income_six.isChecked = true
            }
            resources.getString(R.string.rp_12_500_000_rp_15_000_000) -> {
                alumni_three_radio_income_seven.isChecked = true
            }
            resources.getString(R.string.rp_15_000_000) -> {
                alumni_three_radio_income_eight.isChecked = true
            }
        }

        when (data.isMajorRelated) {
            resources.getString(R.string.prompt_yes) -> {
                alumni_three_radio_major_related_yes.isChecked = true
            }
            resources.getString(R.string.prompt_no) -> {
                alumni_three_radio_major_related_no.isChecked = true
            }
        }

        when (data.institutionNeeds) {
            resources.getString(R.string.prompt_very_high) -> {
                alumni_three_radio_institution_need_very.isChecked = true
            }
            resources.getString(R.string.prompt_high) -> {
                alumni_three_radio_institution_need_yes.isChecked = true
            }
            resources.getString(R.string.prompt_low) -> {
                alumni_three_radio_institution_need_less.isChecked = true
            }
            resources.getString(R.string.prompt_very_low) -> {
                alumni_three_radio_institution_need_no.isChecked = true
            }
        }

        when (data.previousWork) {
            resources.getString(R.string.prompt_yes) -> {
                alumni_three_radio_previous_work_yes.isChecked = true
            }
            resources.getString(R.string.prompt_no) -> {
                alumni_three_radio_previous_work_no.isChecked = true
            }
        }

        if (data.previousWork == resources.getString(R.string.prompt_yes)) {
            alumni_three_layout_previous_work.visibility = View.VISIBLE
            alumni_three_et_number_change_job.setText(data.previousWorkDetail?.numberTimesChangeJobs.toString())

            when (data.previousWorkDetail!!.reasonChangeJob) {
                "Gaji tidak sesuai dengan beban kerja" -> {
                    alumni_three_radio_reason_change_one.isChecked = true
                }
                "Bidang pekerjaan tidak sesuai" -> {
                    alumni_three_radio_reason_change_two.isChecked = true
                }
                "Mencari tantangan dan pengalaman baru" -> {
                    alumni_three_radio_reason_change_three.isChecked = true
                }
                "Tidak cocok dengan rekan kerja" -> {
                    alumni_three_radio_reason_change_four.isChecked = true
                }
                "Gaji terlalu kecil" -> {
                    alumni_three_radio_reason_change_five.isChecked = true
                }
                else -> {
                    alumni_three_radio_reason_change_other.isChecked = true
                    alumni_three_et_reason_change_other.setText(data.previousWorkDetail?.reasonChangeJob)
                }
            }

            alumni_three_et_name_previous_work.setText(data.previousWorkDetail?.firstJobName)
            alumni_three_et_name_previous_position.setText(data.previousWorkDetail!!.firstPlacementJob)

            for (i in listYear.indices) {
                if (listYear[i] == data.previousWorkDetail!!.yearStart.toString()) {
                    selectionYearStart = i
                }
                if (listYear[i] == data.previousWorkDetail!!.yearQuit.toString()) {
                    selectionYearQuit = i
                }
            }

            for (i in monthArray.indices) {
                if (monthArray[i] == data.previousWorkDetail!!.monthStart.toString()) {
                    selectionMonthStart = i
                }
                if (monthArray[i] == data.previousWorkDetail!!.monthQuit.toString()) {
                    selectionMonthQuit = i
                }
            }

            alumni_three_spinner_list_month_entrance_previous.setSelection(selectionMonthStart)
            alumni_three_spinner_list_year_entrance_previous.setSelection(selectionYearStart)
            alumni_three_spinner_list_month_stop_previous.setSelection(selectionMonthQuit)
            alumni_three_spinner_list_year_stop_previous.setSelection(selectionYearQuit)

            when (data.previousWorkDetail!!.effort) {
                "Aktif (mencari sendiri)" -> {
                    alumni_three_radio_effort_previous_active.isChecked = true
                }
                "Pasif (ditawari pekerjaan)" -> {
                    alumni_three_radio_effort_previous_passive.isChecked = true
                }
            }

            originInfoPrevious = StringManipulation().changeToList(
                data.previousWorkDetail?.originInfoCurrentWork!!,
                ';'
            )

            for (s in originInfoPrevious) {
                when (s) {
                    "Iklan" -> alumni_three_checked_box_origin_info_previous_one.isChecked = true
                    "Internet" -> alumni_three_checked_box_origin_info_previous_two.isChecked = true
                    "Almamater/alumni" -> alumni_three_checked_box_origin_info_previous_three.isChecked =
                        true
                    "Koneksi (teman, dosen, saudara/keluarga, dll)" -> alumni_three_checked_box_origin_info_previous_four.isChecked =
                        true
                    "Info lowongan dari kampus" -> alumni_three_checked_box_origin_info_previous_five.isChecked =
                        true
                    else -> {
                        alumni_three_checked_box_origin_info_previous_other.isChecked = true
                        alumni_three_et_origin_info_previous_other.visibility = View.VISIBLE
                        alumni_three_et_origin_info_previous_other.setText(s)
                    }
                }
            }

            when (data.previousWorkDetail?.currentWorkExpectation) {
                "Sangat sesuai dengan harapan" -> {
                    alumni_three_radio_expectation_previous_very.isChecked = true
                }
                "Sesuai harapan" -> {
                    alumni_three_radio_expectation_previous_yes.isChecked = true
                }
                "Kurang sesuai harapan" -> {
                    alumni_three_radio_expectation_previous_less.isChecked = true
                }
                "Tidak sesuai harapan" -> {
                    alumni_three_radio_expectation_previous_no.isChecked = true
                }
            }

            when (data.previousWorkDetail?.isSatisfied) {
                resources.getString(R.string.prompt_very_satisfied) -> {
                    alumni_three_radio_satisfied_previous_very.isChecked = true
                }
                resources.getString(R.string.prompt_satisfied) -> {
                    alumni_three_radio_satisfied_previous_yes.isChecked = true
                }
                resources.getString(R.string.prompt_less_satisfied) -> {
                    alumni_three_radio_satisfied_previous_less.isChecked = true
                }
                resources.getString(R.string.prompt_not_satisfied) -> {
                    alumni_three_radio_satisfied_previous_no.isChecked = true
                }
            }

            when (data.previousWorkDetail?.reasonCurrentWork) {
                "Gaji memadai" -> {
                    alumni_three_radio_reason_previous_one.isChecked = true
                }
                "Sesuai bidang keilmuan" -> {
                    alumni_three_radio_reason_previous_two.isChecked = true
                }
                "Mendapatkan pengalaman" -> {
                    alumni_three_radio_reason_previous_three.isChecked = true
                }
                "Mendapatkan ilmu pengetahuan" -> {
                    alumni_three_radio_reason_previous_four.isChecked = true
                }
                "Mendapatkan keterampilan" -> {
                    alumni_three_radio_reason_previous_five.isChecked = true
                }
                else -> {
                    alumni_three_radio_reason_previous_other.isChecked = true
                    alumni_three_et_reason_previous_other.setText(data.previousWorkDetail?.reasonCurrentWork)
                }
            }

            when (data.previousWorkDetail?.incomeAverage) {
                resources.getString(R.string.rp_1_000_000) -> {
                    alumni_three_radio_income_previous_one.isChecked = true
                }
                resources.getString(R.string.rp_1_000_000_rp_3_000_000) -> {
                    alumni_three_radio_income_previous_two.isChecked = true
                }
                resources.getString(R.string.rp_3_000_000_rp_5_000_000) -> {
                    alumni_three_radio_income_previous_three.isChecked = true
                }
                resources.getString(R.string.rp_5_000_000_rp_7_500_000) -> {
                    alumni_three_radio_income_previous_four.isChecked = true
                }
                resources.getString(R.string.rp_7_500_000_rp_10_000_000) -> {
                    alumni_three_radio_income_previous_five.isChecked = true
                }
                resources.getString(R.string.rp_10_000_000_rp_12_500_000) -> {
                    alumni_three_radio_income_previous_six.isChecked = true
                }
                resources.getString(R.string.rp_12_500_000_rp_15_000_000) -> {
                    alumni_three_radio_income_previous_seven.isChecked = true
                }
                resources.getString(R.string.rp_15_000_000) -> {
                    alumni_three_radio_income_previous_eight.isChecked = true
                }
            }

            val whenStartList = resources.getStringArray(R.array.list_when_looking)
            var selectionWhenStart = -1
            for (i in whenStartList.indices) {
                if (whenStartList[i] == data.previousWorkDetail!!.whenStartLookingJob) {
                    selectionWhenStart = i
                }
            }

            alumni_three_spinner_when_look_previous.setSelection(selectionWhenStart)

            alumni_three_et_number_applied_previous.setText(data.previousWorkDetail?.numberCompaniesApplied.toString())

            when (data.previousWorkDetail?.durationGetFirstJob) {
                resources.getString(R.string._3_bulan) -> alumni_three_radio_duration_get_previous_one.isChecked =
                    true
                resources.getString(R.string._6_bulan) -> alumni_three_radio_duration_get_previous_two.isChecked =
                    true
                "1 Tahun" -> alumni_three_radio_duration_get_previous_three.isChecked = true
                "> 1 Tahun" -> alumni_three_radio_duration_get_previous_four.isChecked = true
            }
        }
    }

    private fun checkAndGetData(): Boolean {
        var valid = true

        data.name = alumni_three_et_name_work.text.toString()
        if (data.name.isEmpty()) {
            alumni_three_et_name_work.error = resources.getString(R.string.prompt_alert_required)
            valid = false
        } else {
            alumni_three_et_name_work.error = null
        }

        if (alumni_three_radio_group_type_work.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_type_work.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            if (selectedRadioButton != alumni_three_radio_type_work_other) {
                data.type = "${selectedRadioButton.text}"
            } else {
                if (alumni_three_et_type_work_other.text.toString().isEmpty()) {
                    alumni_three_et_type_work_other.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_type_work_other.error = null
                    data.type = "${alumni_three_et_type_work_other.text}"
                }
            }
        } else {
            valid = false
            alumni_three_radio_group_type_work.findFocus()
        }

        data.monthStart = alumni_three_spinner_list_month_entrance.selectedItem.toString().toInt()
        data.yearStart = alumni_three_spinner_list_year_entrance.selectedItem.toString().toInt()
        data.monthQuit = alumni_three_spinner_list_month_stop.selectedItem.toString()
        data.yearQuit = alumni_three_spinner_list_year_stop.selectedItem.toString()

        if (alumni_three_radio_group_effort.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_effort.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.effort = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_effort.findFocus()
        }

        if (alumni_three_checked_box_origin_info_one.isChecked) {
            originInfo.add(alumni_three_checked_box_origin_info_one.text.toString())
        } else {
            originInfo.remove(alumni_three_checked_box_origin_info_one.text.toString())
        }
        if (alumni_three_checked_box_origin_info_two.isChecked) {
            originInfo.add(alumni_three_checked_box_origin_info_two.text.toString())
        } else {
            originInfo.remove(alumni_three_checked_box_origin_info_two.text.toString())
        }
        if (alumni_three_checked_box_origin_info_three.isChecked) {
            originInfo.add(alumni_three_checked_box_origin_info_three.text.toString())
        } else {
            originInfo.remove(alumni_three_checked_box_origin_info_three.text.toString())
        }
        if (alumni_three_checked_box_origin_info_four.isChecked) {
            originInfo.add(alumni_three_checked_box_origin_info_four.text.toString())
        } else {
            originInfo.remove(alumni_three_checked_box_origin_info_four.text.toString())
        }
        if (alumni_three_checked_box_origin_info_five.isChecked) {
            originInfo.add(alumni_three_checked_box_origin_info_five.text.toString())
        } else {
            originInfo.remove(alumni_three_checked_box_origin_info_five.text.toString())
        }
        if (alumni_three_checked_box_origin_info_other.isChecked) {
            if (alumni_three_et_origin_info_other.text.toString().isEmpty()) {
                valid = false
                alumni_three_et_origin_info_other.error =
                    resources.getString(R.string.prompt_alert_required)
            } else {
                alumni_three_et_origin_info_other.error = null
                originInfoOther = alumni_three_et_origin_info_other.text.toString()
                originInfo.add(originInfoOther)
            }
        } else {
            originInfo.remove(originInfoOther)
            originInfoOther = ""
        }
        if (originInfo.size == 0) {
            valid = false
        } else {
            for (i in originInfo.indices) {
                data.originInfoCurrentWork += originInfo[i]
                if (originInfo.size - 1 != i) {
                    data.originInfoCurrentWork += "; "
                }
            }
        }

        if (alumni_three_radio_group_expectation.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_expectation.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.currentWorkExpectation = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_expectation.findFocus()
        }

        if (alumni_three_radio_group_satisfied.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_satisfied.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.isSatisfied = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_satisfied.findFocus()
        }

        if (alumni_three_radio_group_reason.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_reason.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            if (selectedRadioButton != alumni_three_radio_reason_other) {
                data.reasonCurrentWork = "${selectedRadioButton.text}"
            } else {
                if (alumni_three_et_reason_other.text.toString().isEmpty()) {
                    alumni_three_et_reason_other.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_reason_other.error = null
                    data.reasonCurrentWork = "${alumni_three_et_reason_other.text}"
                }
            }
        } else {
            valid = false
            alumni_three_radio_group_reason.findFocus()
        }

        if (alumni_three_radio_group_income.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_income.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.incomeAverage = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_income.findFocus()
        }

        if (alumni_three_radio_group_major_related.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_major_related.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.isMajorRelated = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_major_related.findFocus()
        }

        if (alumni_three_radio_group_institution_need.checkedRadioButtonId != -1) {
            val selectedId = alumni_three_radio_group_institution_need.checkedRadioButtonId
            val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
            data.institutionNeeds = "${selectedRadioButton.text}"
        } else {
            valid = false
            alumni_three_radio_group_institution_need.findFocus()
        }

        if (alumni_three_radio_group_previous_work.checkedRadioButtonId != -1) {
            val selectedIdS = alumni_three_radio_group_previous_work.checkedRadioButtonId
            val selectedRadioButtonS = activity!!.findViewById(selectedIdS) as RadioButton
            data.previousWork = "${selectedRadioButtonS.text}"
            if (data.previousWork.equals("iya", true)) {
                data.previousWorkDetail =
                    AlumniQuizThreePreviousWork()
                if (alumni_three_et_number_change_job.text.toString().isEmpty()) {
                    alumni_three_et_number_change_job.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_number_change_job.error = null
                    data.previousWorkDetail?.numberTimesChangeJobs =
                        alumni_three_et_number_change_job.text.toString().toInt()
                }

                if (alumni_three_radio_group_reason_change.checkedRadioButtonId != -1) {
                    val selectedId = alumni_three_radio_group_reason_change.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    if (selectedRadioButton != alumni_three_radio_reason_change_other) {
                        data.previousWorkDetail?.reasonChangeJob = "${selectedRadioButton.text}"
                    } else {
                        if (alumni_three_et_reason_change_other.text.toString().isEmpty()) {
                            alumni_three_et_reason_change_other.error =
                                resources.getString(R.string.prompt_alert_required)
                            valid = false
                        } else {
                            alumni_three_et_reason_change_other.error = null
                            data.previousWorkDetail?.reasonChangeJob =
                                "${alumni_three_et_reason_change_other.text}"
                        }
                    }
                } else {
                    valid = false
                    alumni_three_radio_group_reason_change.findFocus()
                }

                if (alumni_three_et_name_previous_work.text.toString().isEmpty()) {
                    alumni_three_et_name_previous_work.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_name_previous_work.error = null
                    data.previousWorkDetail?.firstJobName =
                        alumni_three_et_name_previous_work.text.toString()
                }

                if (alumni_three_et_name_previous_position.text.toString().isEmpty()) {
                    alumni_three_et_name_previous_position.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_name_previous_position.error = null
                    data.previousWorkDetail?.firstPlacementJob =
                        alumni_three_et_name_previous_position.text.toString()
                }

                data.previousWorkDetail?.monthStart =
                    alumni_three_spinner_list_month_entrance_previous.selectedItem.toString()
                        .toInt()
                data.previousWorkDetail?.yearStart =
                    alumni_three_spinner_list_year_entrance_previous.selectedItem.toString().toInt()
                data.previousWorkDetail?.monthQuit =
                    alumni_three_spinner_list_month_stop_previous.selectedItem.toString().toInt()
                data.previousWorkDetail?.yearQuit =
                    alumni_three_spinner_list_year_stop_previous.selectedItem.toString().toInt()

                if (alumni_three_radio_group_effort_previous.checkedRadioButtonId != -1) {
                    val selectedId = alumni_three_radio_group_effort_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    data.previousWorkDetail?.effort = "${selectedRadioButton.text}"
                } else {
                    valid = false
                    alumni_three_radio_group_effort_previous.findFocus()
                }

                if (alumni_three_checked_box_origin_info_previous_one.isChecked) {
                    originInfoPrevious.add(alumni_three_checked_box_origin_info_previous_one.text.toString())
                } else {
                    originInfoPrevious.remove(alumni_three_checked_box_origin_info_previous_one.text.toString())
                }
                if (alumni_three_checked_box_origin_info_previous_two.isChecked) {
                    originInfoPrevious.add(alumni_three_checked_box_origin_info_previous_two.text.toString())
                } else {
                    originInfoPrevious.remove(alumni_three_checked_box_origin_info_previous_two.text.toString())
                }
                if (alumni_three_checked_box_origin_info_previous_three.isChecked) {
                    originInfoPrevious.add(alumni_three_checked_box_origin_info_previous_three.text.toString())
                } else {
                    originInfoPrevious.remove(alumni_three_checked_box_origin_info_previous_three.text.toString())
                }
                if (alumni_three_checked_box_origin_info_previous_four.isChecked) {
                    originInfoPrevious.add(alumni_three_checked_box_origin_info_previous_four.text.toString())
                } else {
                    originInfoPrevious.remove(alumni_three_checked_box_origin_info_previous_four.text.toString())
                }
                if (alumni_three_checked_box_origin_info_previous_five.isChecked) {
                    originInfoPrevious.add(alumni_three_checked_box_origin_info_previous_five.text.toString())
                } else {
                    originInfoPrevious.remove(alumni_three_checked_box_origin_info_previous_five.text.toString())
                }
                if (alumni_three_checked_box_origin_info_previous_other.isChecked) {
                    if (alumni_three_et_origin_info_previous_other.text.toString().isEmpty()) {
                        alumni_three_et_origin_info_previous_other.error =
                            resources.getString(R.string.prompt_alert_required)
                        valid = false
                    } else {
                        alumni_three_et_origin_info_previous_other.error = null
                        originInfoOtherPrevious =
                            alumni_three_et_origin_info_previous_other.text.toString()
                        originInfoPrevious.add(originInfoOtherPrevious)
                    }
                } else {
                    originInfoPrevious.remove(originInfoOtherPrevious)
                    originInfoOtherPrevious = ""
                }
                if (originInfoPrevious.size == 0) {
                    valid = false
                } else {
                    for (i in originInfoPrevious.indices) {
                        data.previousWorkDetail!!.originInfoCurrentWork += originInfoPrevious[i]
                        if (originInfoPrevious.size - 1 != i) {
                            data.previousWorkDetail!!.originInfoCurrentWork += "; "
                        }
                    }
                }

                if (alumni_three_radio_group_expectation_previous.checkedRadioButtonId != -1) {
                    val selectedId =
                        alumni_three_radio_group_expectation_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    data.previousWorkDetail?.currentWorkExpectation = "${selectedRadioButton.text}"
                } else {
                    valid = false
                    alumni_three_radio_group_expectation_previous.findFocus()
                }

                if (alumni_three_radio_group_satisfied_previous.checkedRadioButtonId != -1) {
                    val selectedId =
                        alumni_three_radio_group_satisfied_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    data.previousWorkDetail?.isSatisfied = "${selectedRadioButton.text}"
                } else {
                    valid = false
                    alumni_three_radio_group_satisfied_previous.findFocus()
                }

                if (alumni_three_radio_group_reason_previous.checkedRadioButtonId != -1) {
                    val selectedId = alumni_three_radio_group_reason_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    if (selectedRadioButton != alumni_three_radio_reason_previous_other) {
                        data.reasonCurrentWork = "${selectedRadioButton.text}"
                    } else {
                        if (alumni_three_et_reason_previous_other.text.toString().isEmpty()) {
                            alumni_three_et_reason_previous_other.error =
                                resources.getString(R.string.prompt_alert_required)
                            valid = false
                        } else {
                            alumni_three_et_reason_previous_other.error = null
                            data.previousWorkDetail?.reasonCurrentWork =
                                "${alumni_three_et_reason_previous_other.text}"
                        }
                    }
                } else {
                    valid = false
                    alumni_three_radio_group_reason_previous.findFocus()
                }

                if (alumni_three_radio_group_income_previous.checkedRadioButtonId != -1) {
                    val selectedId = alumni_three_radio_group_income_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    data.previousWorkDetail?.incomeAverage = "${selectedRadioButton.text}"
                } else {
                    valid = false
                    alumni_three_radio_group_income_previous.findFocus()
                }

                data.previousWorkDetail?.whenStartLookingJob =
                    alumni_three_spinner_when_look_previous.selectedItem.toString()

                if (alumni_three_et_number_applied_previous.text.toString().isEmpty()) {
                    alumni_three_et_number_applied_previous.error =
                        resources.getString(R.string.prompt_alert_required)
                    valid = false
                } else {
                    alumni_three_et_number_applied_previous.error = null
                    data.previousWorkDetail?.numberCompaniesApplied =
                        alumni_three_et_number_applied_previous.text.toString().toInt()
                }

                if (alumni_three_radio_group_duration_get_previous.checkedRadioButtonId != -1) {
                    val selectedId =
                        alumni_three_radio_group_duration_get_previous.checkedRadioButtonId
                    val selectedRadioButton = activity!!.findViewById(selectedId) as RadioButton
                    data.previousWorkDetail?.durationGetFirstJob = "${selectedRadioButton.text}"
                } else {
                    valid = false
                    alumni_three_radio_group_duration_get_previous.findFocus()
                }
            } else {
                data.previousWorkDetail = null
            }
        } else {
            valid = false
            alumni_three_radio_group_previous_work.findFocus()
        }

        return valid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alumni_three, container, false)

        val radioPreviousYes =
            view.findViewById<RadioButton>(R.id.alumni_three_radio_previous_work_yes)
        val previousLayout = view.findViewById<LinearLayout>(R.id.alumni_three_layout_previous_work)
        val checkOne = view.findViewById<CheckBox>(R.id.alumni_three_checked_box_origin_info_other)
        val editOne = view.findViewById<EditText>(R.id.alumni_three_et_origin_info_other)
        val checkTwo =
            view.findViewById<CheckBox>(R.id.alumni_three_checked_box_origin_info_previous_other)
        val editTwo = view.findViewById<EditText>(R.id.alumni_three_et_origin_info_previous_other)
//        val radioOne = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_yes)
//        val radioGroupOne = view.findViewById<RadioGroup>(R.id.alumni_three_radio_group_position_yes)
//        val radioOneOther = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_no_other)
//        val radioTwo = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_no)
//        val radioGroupTwo = view.findViewById<RadioGroup>(R.id.alumni_three_radio_group_position_no)
//        val radioTwoOther = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_yes_other)
//        val editPosition = view.findViewById<EditText>(R.id.alumni_three_et_position_other)
        val radioThree = view.findViewById<RadioButton>(R.id.alumni_three_radio_reason_other)
        val editReason = view.findViewById<EditText>(R.id.alumni_three_et_reason_other)
        val radioFour = view.findViewById<RadioButton>(R.id.alumni_three_radio_reason_change_other)
        val editReasonChange = view.findViewById<EditText>(R.id.alumni_three_et_reason_change_other)
        val radioFive =
            view.findViewById<RadioButton>(R.id.alumni_three_radio_reason_previous_other)
        val editReasonPre = view.findViewById<EditText>(R.id.alumni_three_et_reason_previous_other)
        val radioSix = view.findViewById<RadioButton>(R.id.alumni_three_radio_type_work_other)
        val editTypeWork = view.findViewById<EditText>(R.id.alumni_three_et_type_work_other)

        radioSix.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editTypeWork.visibility = View.VISIBLE
                } else {
                    editTypeWork.visibility = View.GONE
                }
            }
        }

        radioFive.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editReasonPre.visibility = View.VISIBLE
                } else {
                    editReasonPre.visibility = View.GONE
                }
            }
        }

        radioFour.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editReasonChange.visibility = View.VISIBLE
                } else {
                    editReasonChange.visibility = View.GONE
                }
            }
        }

        radioThree.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editReason.visibility = View.VISIBLE
                } else {
                    editReason.visibility = View.GONE
                }
            }
        }

        radioPreviousYes.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    previousLayout.visibility = View.VISIBLE
                } else {
                    previousLayout.visibility = View.GONE
                }
            }
        }

        /*radioOne.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    radioGroupOne.visibility = View.VISIBLE
                } else {
                    radioGroupOne.visibility = View.GONE
                }
            }
        }*/

//        radioTwo.setOnClickListener { v ->
//            if (v is RadioButton) {
//                if (v.isChecked) {
//                    radioGroupTwo.visibility = View.VISIBLE
//                } else {
//                    radioGroupTwo.visibility = View.GONE
//                }
//            }
//        }

//        radioOneOther.setOnClickListener { v ->
//            if (v is RadioButton) {
//                if (v.isChecked) {
//                    editPosition.visibility = View.VISIBLE
//                } else {
//                    editPosition.visibility = View.GONE
//                }
//            }
//        }

        /*radioTwoOther.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editPosition.visibility = View.VISIBLE
                } else {
                    editPosition.visibility = View.GONE
                }
            }
        }*/

        checkOne.setOnClickListener { v ->
            if (v is CheckBox) {
                editOne.visibility = if (v.isChecked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        checkTwo.setOnClickListener { v ->
            if (v is CheckBox) {
                editTwo.visibility = if (v.isChecked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        return view
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
        fun onFragmentInteraction(data: AlumniQuizThree)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            AlumniThreeFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
