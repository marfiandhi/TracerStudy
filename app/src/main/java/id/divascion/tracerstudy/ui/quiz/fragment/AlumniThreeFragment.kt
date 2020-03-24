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
import kotlinx.android.synthetic.main.fragment_alumni_three.*

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class AlumniThreeFragment : Fragment() {

    private lateinit var data: AlumniQuizThree
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

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteText =
            "<font color=#FFFFFF>Pertanyaan di bawah ini </font><font color=#FF0000>terkait</font><font color=#FFFFFF> dengan </font><font color=#75FF1A>pekerjaan pertama</font><font color=#FFFFFF>.</font>"
        alumni_three_tv_note.text = Html.fromHtml(noteText)
        radio()
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

        alumni_three_radio_group_position.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.alumni_three_radio_position_yes) {
                alumni_three_radio_group_position_yes.visibility = View.VISIBLE
                alumni_three_radio_group_position_no.visibility = View.GONE
            } else {
                alumni_three_radio_group_position_yes.visibility = View.GONE
                alumni_three_radio_group_position_no.visibility = View.VISIBLE
            }
        }

        alumni_three_radio_group_position_yes.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_position_other.visibility =
                if (checkedId == R.id.alumni_three_radio_position_yes_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

        alumni_three_radio_group_position_no.setOnCheckedChangeListener { _, checkedId ->
            alumni_three_et_position_other.visibility =
                if (checkedId == R.id.alumni_three_radio_position_no_other) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

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
        val radioOne = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_yes)
        val radioGroupOne =
            view.findViewById<RadioGroup>(R.id.alumni_three_radio_group_position_yes)
        val radioOneOther =
            view.findViewById<RadioButton>(R.id.alumni_three_radio_position_no_other)
        val radioTwo = view.findViewById<RadioButton>(R.id.alumni_three_radio_position_no)
        val radioGroupTwo = view.findViewById<RadioGroup>(R.id.alumni_three_radio_group_position_no)
        val radioTwoOther =
            view.findViewById<RadioButton>(R.id.alumni_three_radio_position_yes_other)
        val editPosition = view.findViewById<EditText>(R.id.alumni_three_et_position_other)
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

        radioOne.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    radioGroupOne.visibility = View.VISIBLE
                } else {
                    radioGroupOne.visibility = View.GONE
                }
            }
        }

        radioTwo.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    radioGroupTwo.visibility = View.VISIBLE
                } else {
                    radioGroupTwo.visibility = View.GONE
                }
            }
        }

        radioOneOther.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editPosition.visibility = View.VISIBLE
                } else {
                    editPosition.visibility = View.GONE
                }
            }
        }

        radioTwoOther.setOnClickListener { v ->
            if (v is RadioButton) {
                if (v.isChecked) {
                    editPosition.visibility = View.VISIBLE
                } else {
                    editPosition.visibility = View.GONE
                }
            }
        }

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
        fun newInstance(param1: String, param2: String) =
            AlumniThreeFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
