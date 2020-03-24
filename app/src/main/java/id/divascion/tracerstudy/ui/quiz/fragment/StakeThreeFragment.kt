package id.divascion.tracerstudy.ui.quiz.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseUser

import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.StakeQuizOne
import id.divascion.tracerstudy.data.model.StakeQuizThree

private const val ARG_PARAM1 = "USER"
private const val ARG_PARAM2 = "STATUS"

class StakeThreeFragment : Fragment() {

    private lateinit var data: StakeQuizThree
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stake_three, container, false)
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
        fun onFragmentInteraction(data: StakeQuizThree)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StakeThreeFragment().apply {
                arguments = Bundle().apply {
                    putString("done", param1)
                }
            }
    }
}
