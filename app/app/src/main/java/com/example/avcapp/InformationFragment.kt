package com.example.avcapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InformationFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_information, container, false) as View

        val strokeSymptoms = view.findViewById(R.id.strokeSymptoms) as Button
        val fast = view.findViewById(R.id.fast) as Button

        val fragmentManager = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.add(R.id.fragmentInformation, StrokeSymptomsFragment())
        fragmentTransaction.commit()

        strokeSymptoms.setOnClickListener{
            strokeSymptoms.setBackgroundResource(R.drawable.information_button_active)
            strokeSymptoms.setTextColor(Color.WHITE)
            fast.setBackgroundResource(R.drawable.information_button)
            fast.setTextColor(resources.getColor(R.color.titleColor))

            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            ft.replace(R.id.fragmentInformation, StrokeSymptomsFragment())
            ft.commit()
        }

        fast.setOnClickListener{
            fast.setBackgroundResource(R.drawable.information_button_active)
            fast.setTextColor(Color.WHITE)
            strokeSymptoms.setBackgroundResource(R.drawable.information_button)
            strokeSymptoms.setTextColor(resources.getColor(R.color.titleColor))

            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            ft.replace(R.id.fragmentInformation, FastFragment())
            ft.commit()
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
         * @return A new instance of fragment InformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
