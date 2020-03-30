package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseUser

import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniQuizFour
import kotlinx.android.synthetic.main.fragment_alumni_four.*

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniFourFragment : Fragment() {

    private lateinit var data: AlumniQuizFour
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
        alumni_four_radio_group_useful.setOnCheckedChangeListener { _, checkedId ->
            alumni_four_et_useful_no.visibility =
                if (checkedId == R.id.alumni_four_radio_useful_no) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        /*alumni_four_radio_group_major.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.alumni_four_radio_major_one -> {
                    alumni_four_checked_box_group_expert_one.visibility = View.VISIBLE
                    alumni_four_checked_box_group_expert_two.visibility = View.GONE
                    alumni_four_checked_box_group_expert_three.visibility = View.GONE
                }
                R.id.alumni_four_radio_major_two -> {
                    alumni_four_checked_box_group_expert_one.visibility = View.GONE
                    alumni_four_checked_box_group_expert_two.visibility = View.VISIBLE
                    alumni_four_checked_box_group_expert_three.visibility = View.GONE
                }
                R.id.alumni_four_radio_major_three -> {
                    alumni_four_checked_box_group_expert_one.visibility = View.GONE
                    alumni_four_checked_box_group_expert_two.visibility = View.GONE
                    alumni_four_checked_box_group_expert_three.visibility = View.VISIBLE
                }
            }
        }
        alumni_four_checked_box_three_expert_other.setOnClickListener { v ->
            if (v is CheckBox) {
                alumni_four_et_expert_other.visibility = if (v.isChecked) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alumni_four, container, false)

        /*val radioOne = view.findViewById<RadioButton>(R.id.alumni_four_radio_major_one)
        val radioTwo = view.findViewById<RadioButton>(R.id.alumni_four_radio_major_two)
        val radioThree = view.findViewById<RadioButton>(R.id.alumni_four_radio_major_three)

        val checkBoxVariableOne =
            view.findViewById<CheckBox>(R.id.alumni_four_checked_box_one_expert_other)
        val checkBoxVariableTwo =
            view.findViewById<CheckBox>(R.id.alumni_four_checked_box_two_expert_other)
        val checkBoxVariableThree =
            view.findViewById<CheckBox>(R.id.alumni_four_checked_box_three_expert_other)

        val editTextExpert = view.findViewById<EditText>(R.id.alumni_four_et_expert_other)

        val linearOne =
            view.findViewById<LinearLayout>(R.id.alumni_four_checked_box_group_expert_one)
        val linearTwo =
            view.findViewById<LinearLayout>(R.id.alumni_four_checked_box_group_expert_two)
        val linearThree =
            view.findViewById<LinearLayout>(R.id.alumni_four_checked_box_group_expert_three)

        radioOne.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    linearOne.visibility = View.VISIBLE
                } else {
                    linearOne.visibility = View.GONE
                }
            }
        }

        radioTwo.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    linearTwo.visibility = View.VISIBLE
                } else {
                    linearTwo.visibility = View.GONE
                }
            }
        }

        radioThree.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    linearThree.visibility = View.VISIBLE
                } else {
                    linearThree.visibility = View.GONE
                }
            }
        }

        checkBoxVariableOne.setOnClickListener { v ->
            if (v is CheckBox) {
                if (v.isChecked) {
                    editTextExpert.visibility = View.VISIBLE
                } else {
                    editTextExpert.visibility = View.GONE
                }
            }
        }

        checkBoxVariableTwo.setOnClickListener { v ->
            if (v is CheckBox) {
                if (v.isChecked) {
                    editTextExpert.visibility = View.VISIBLE
                } else {
                    editTextExpert.visibility = View.GONE
                }
            }
        }

        checkBoxVariableThree.setOnClickListener { v ->
            if (v is CheckBox) {
                if (v.isChecked) {
                    editTextExpert.visibility = View.VISIBLE
                } else {
                    editTextExpert.visibility = View.GONE
                }
            }
        }*/

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
        fun onFragmentInteraction(data: AlumniQuizFour)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumniFourFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
