package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser

import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizTwo
import kotlinx.android.synthetic.main.fragment_alumni_two.*

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniTwoFragment : Fragment() {

    private lateinit var data: AlumniQuizTwo
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
        radio()
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
            alumni_two_layout_school_one.visibility =
                if (checkedId == R.id.alumni_two_radio_study_yes) {
                    View.VISIBLE
                } else {
                    View.GONE
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
