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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alumni_biodata_tv_school_one_name.text = data.quizOne?.schoolName
        var address = "Provinsi ${data.quizOne?.schoolProvince}"
        if (data.quizOne?.schoolCity.toString().isNotEmpty()) {
            address += ", ${data.quizOne?.schoolCity}"
        }
        if (data.quizOne?.schoolPostCode.toString().isNotEmpty()) {
            address += ", Kode Pos ${data.quizOne?.schoolPostCode}"
        }
        alumni_biodata_tv_school_one_address.text = address

        alumni_biodata_tv_campus_one_major.text = data.quizTwo?.majorProgram
        alumni_biodata_tv_work_one_name.text = data.quizThree?.name
        alumni_biodata_tv_work_one_type.text = data.quizThree?.type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alumni_biodata, container, false)
    }

}
