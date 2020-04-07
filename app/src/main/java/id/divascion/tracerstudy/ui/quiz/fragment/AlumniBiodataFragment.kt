package id.divascion.tracerstudy.ui.quiz.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuiz
import kotlinx.android.synthetic.main.fragment_alumni_biodata.*

private const val ARG_PARAM1 = "ITEM"

class AlumniBiodataFragment : Fragment() {

    private lateinit var data: AlumniQuiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable(ARG_PARAM1) ?: AlumniQuiz()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectData()
    }

    private fun injectData() {
        alumni_biodata_tv_born_province.text = data.quizOne?.bornProvince
        alumni_biodata_tv_born_city.text = data.quizOne?.bornCity
        alumni_biodata_tv_born_date.text = data.quizOne?.bornDate

        alumni_biodata_tv_address_province.text = data.quizOne?.addressProvince
        alumni_biodata_tv_address_city.text = data.quizOne?.addressCity
        alumni_biodata_tv_address_district.text = data.quizOne?.addressDistrict
        alumni_biodata_tv_address_village.text = data.quizOne?.addressVillage
        alumni_biodata_tv_address_street.text = data.quizOne?.addressStreet
        var addressPostcode = data.quizOne?.addressPostCode.toString()
        if (addressPostcode.isEmpty()) {
            addressPostcode = "-"
        }
        alumni_biodata_tv_address_postcode.text = addressPostcode
        var addressPhone = data.quizOne?.addressPhone.toString()
        if (addressPhone.isEmpty()) {
            addressPhone = "-"
        }
        alumni_biodata_tv_address_phone.text = addressPhone
        alumni_biodata_tv_address_hp.text = data.quizOne?.addressMobilePhone
        alumni_biodata_tv_email.text = data.quizOne?.email
        var facebook = data.quizOne?.facebook.toString()
        if (facebook.isEmpty()) {
            facebook = "-"
        }
        alumni_biodata_tv_fb.text = facebook

        var schoolName = data.quizOne?.schoolName.toString()
        if (schoolName.isEmpty()) {
            schoolName = "-"
        }
        var schoolProvince = data.quizOne?.schoolProvince.toString()
        if (schoolProvince.isEmpty()) {
            schoolProvince = "-"
        }
        var schoolCity = data.quizOne?.schoolCity.toString()
        if (schoolCity.isEmpty()) {
            schoolCity = "-"
        }
        var schoolPostCode = data.quizOne?.schoolPostCode.toString()
        if (schoolPostCode.isEmpty()) {
            schoolPostCode = "-"
        }
        alumni_biodata_tv_school_one_name.text = schoolName
        alumni_biodata_tv_school_one_province.text = schoolProvince
        alumni_biodata_tv_school_one_city.text = schoolCity
        alumni_biodata_tv_school_one_postcode.text = schoolPostCode

        alumni_biodata_tv_campus_one_major.text = data.quizTwo?.majorProgram
        alumni_biodata_tv_campus_one_entrance.text = data.quizTwo?.yearEntrance.toString()
        val graduate = "Bulan ${data.quizTwo?.monthGraduate}/Tahun ${data.quizTwo?.yearGraduate}"
        alumni_biodata_tv_campus_one_graduate.text = graduate
        alumni_biodata_tv_campus_one_organ.text = data.quizTwo?.isOrganization
        alumni_biodata_tv_campus_one_gpi.text = data.quizTwo?.GPI

        if (data.quizTwo?.afterGraduate.equals("tidak", true)) {
            alumni_biodata_layout_campus_other.visibility = View.GONE
            alumni_biodata_layout_campus_other_two.visibility = View.GONE
        } else {
            alumni_biodata_layout_campus_other.visibility = View.VISIBLE
            alumni_biodata_layout_campus_other_two.visibility = View.VISIBLE
            alumni_biodata_tv_campus_two_name.text = data.quizTwo?.afterGraduateDetail?.oneName
            alumni_biodata_tv_campus_two_city.text =
                data.quizTwo?.afterGraduateDetail?.oneCountryCity
            alumni_biodata_tv_campus_two_city_major.text =
                data.quizTwo?.afterGraduateDetail?.oneMajor
            alumni_biodata_tv_campus_two_level.text = data.quizTwo?.afterGraduateDetail?.oneLevel
            alumni_biodata_tv_campus_two_entrance_graduate.text =
                data.quizTwo?.afterGraduateDetail?.oneEntranceGraduate
            alumni_biodata_tv_campus_two_reason.text = data.quizTwo?.afterGraduateDetail?.reason

            var campusName = data.quizTwo?.afterGraduateDetail?.twoName.toString()
            if (campusName.isEmpty()) {
                campusName = "-"
            }
            var campusCity = data.quizTwo?.afterGraduateDetail?.twoCountryCity.toString()
            if (campusCity.isEmpty()) {
                campusCity = "-"
            }
            var campusMajor = data.quizTwo?.afterGraduateDetail?.twoMajor.toString()
            if (campusMajor.isEmpty()) {
                campusMajor = "-"
            }
            var campusLevel = data.quizTwo?.afterGraduateDetail?.twoLevel.toString()
            if (campusLevel.isEmpty()) {
                campusLevel = "-"
            }
            var campusEntrance = data.quizTwo?.afterGraduateDetail?.twoEntranceGraduate.toString()
            if (campusEntrance.isEmpty()) {
                campusEntrance = "-"
            }
            alumni_biodata_tv_campus_three_name.text = campusName
            alumni_biodata_tv_campus_three_city.text = campusCity
            alumni_biodata_tv_campus_three_city_major.text = campusMajor
            alumni_biodata_tv_campus_three_level.text = campusLevel
            alumni_biodata_tv_campus_three_entrance_graduate.text = campusEntrance
        }

        alumni_biodata_tv_expect_work.text = data.quizTwo?.jobExpected
        alumni_biodata_tv_placement.text = data.quizTwo?.placementGraduate
        alumni_biodata_tv_apply_job.text = data.quizTwo?.applyJob
        alumni_biodata_tv_time_apply_job.text = data.quizTwo?.timeApplyJob
        alumni_biodata_tv_make_cv.text = data.quizTwo?.makeCV
        alumni_biodata_tv_time_make_cv.text = data.quizTwo?.timeMakeCV
        alumni_biodata_tv_work_when_graduate.text = data.quizTwo?.work

        alumni_biodata_tv_work_one_name.text = data.quizThree?.name
        alumni_biodata_tv_work_one_type.text = data.quizThree?.type
        var workAddress = data.quizOne?.officeAddress.toString()
        if (workAddress.isEmpty()) {
            workAddress = "-"
        }
        alumni_biodata_tv_work_one_address.text = workAddress
        var workPhone = data.quizOne?.officePhone.toString()
        if (workPhone.isEmpty()) {
            workPhone = "-"
        }
        alumni_biodata_tv_work_one_phone.text = workPhone
        var workPostcode = data.quizOne?.officePostCode.toString()
        if (workPostcode.isEmpty() || workPostcode == "null") {
            workPostcode = "-"
        }
        alumni_biodata_tv_work_one_postcode.text = workPostcode
        val workStart = "Bulan ${data.quizThree?.monthStart}, Tahun ${data.quizThree?.yearStart}"
        alumni_biodata_tv_work_one_start.text = workStart
        var workQuit = "Bulan ${data.quizThree?.monthQuit}, Tahun ${data.quizThree?.yearQuit}"
        if (data.quizThree?.monthQuit.equals("-", true) || data.quizThree?.yearQuit.equals(
                "-",
                true
            )
        ) {
            workQuit = "Masih Bekerja"
        }
        alumni_biodata_tv_work_one_quit.text = workQuit
        alumni_biodata_tv_work_one_effort.text = data.quizThree?.effort
        alumni_biodata_tv_work_one_origin_info.text = data.quizThree?.originInfoCurrentWork
        alumni_biodata_tv_work_one_origin_expectation.text = data.quizThree?.currentWorkExpectation
        alumni_biodata_tv_work_one_satisfied.text = data.quizThree?.isSatisfied
        alumni_biodata_tv_work_one_reason.text = data.quizThree?.reasonCurrentWork
        alumni_biodata_tv_work_one_income.text = data.quizThree?.incomeAverage
        alumni_biodata_tv_work_one_major_related.text = data.quizThree?.isMajorRelated
        alumni_biodata_tv_work_one_need.text = data.quizThree?.institutionNeeds

        if (data.quizThree?.previousWork.equals("tidak", true)) {
            alumni_biodata_layout_work_previous.visibility = View.GONE
        } else {
            alumni_biodata_layout_work_previous.visibility = View.VISIBLE

            alumni_biodata_tv_work_previous_number_change_job.text =
                data.quizThree?.previousWorkDetail?.numberTimesChangeJobs.toString()
            alumni_biodata_tv_work_previous_reason_change.text =
                data.quizThree?.previousWorkDetail?.reasonChangeJob
            alumni_biodata_tv_work_previous_name.text =
                data.quizThree?.previousWorkDetail?.firstJobName
            alumni_biodata_tv_work_previous_placement.text =
                data.quizThree?.previousWorkDetail?.firstPlacementJob
            val workPreviousStart =
                "Bulan ${data.quizThree?.previousWorkDetail?.monthStart}, Tahun ${data.quizThree?.previousWorkDetail?.yearStart}"
            alumni_biodata_tv_work_previous_start.text = workPreviousStart
            val workPreviousQuit =
                "Bulan ${data.quizThree?.previousWorkDetail?.monthQuit}, Tahun ${data.quizThree?.previousWorkDetail?.yearQuit}"
            alumni_biodata_tv_work_previous_quit.text = workPreviousQuit
            alumni_biodata_tv_work_previous_effort.text = data.quizThree?.previousWorkDetail?.effort
            alumni_biodata_tv_work_previous_origin_info.text =
                data.quizThree?.previousWorkDetail?.originInfoCurrentWork
            alumni_biodata_tv_work_previous_origin_expectation.text =
                data.quizThree?.previousWorkDetail?.currentWorkExpectation
            alumni_biodata_tv_work_previous_satisfied.text =
                data.quizThree?.previousWorkDetail?.isSatisfied
            alumni_biodata_tv_work_previous_reason.text =
                data.quizThree?.previousWorkDetail?.reasonCurrentWork
            alumni_biodata_tv_work_previous_income.text =
                data.quizThree?.previousWorkDetail?.incomeAverage
            alumni_biodata_tv_work_previous_start_looking_job.text =
                data.quizThree?.previousWorkDetail?.whenStartLookingJob
            alumni_biodata_tv_work_previous_number_applied.text =
                data.quizThree?.previousWorkDetail?.numberCompaniesApplied.toString()
            alumni_biodata_tv_work_previous_duration.text =
                data.quizThree?.previousWorkDetail?.durationGetFirstJob
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_biodata, container, false)
    }

}
