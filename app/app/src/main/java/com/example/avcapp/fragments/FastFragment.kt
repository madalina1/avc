package com.example.avcapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.avcapp.R
import java.io.BufferedReader
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FastFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fast, container, false) as View

        var faceText = ""
        var armsText = ""
        var speechText = ""
        var timeText = ""

        val reader: BufferedReader? = null

        val faceTextView = view.findViewById(R.id.face_text) as TextView
        val armsTextView = view.findViewById(R.id.arms_text) as TextView
        val speechTextView = view.findViewById(R.id.speech_text) as TextView
        val timeTextView = view.findViewById(R.id.time_text) as TextView

        try {
            faceText = requireContext().assets.open("face_information.txt").bufferedReader().use{
                it.readText()
            }

            armsText = requireContext().assets.open("arms_information.txt").bufferedReader().use{
                it.readText()
            }

            speechText = requireContext().assets.open("speech_information.txt").bufferedReader().use{
                it.readText()
            }

            timeText = requireContext().assets.open("time_information.txt").bufferedReader().use{
                it.readText()
            }
        } catch (e: IOException) {
            Toast.makeText(context, "Error reading license file!", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                //log the exception
                e.printStackTrace()
            }

            faceTextView.text = faceText
            armsTextView.text = armsText
            speechTextView.text = speechText
            timeTextView.text = timeText
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FastFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
