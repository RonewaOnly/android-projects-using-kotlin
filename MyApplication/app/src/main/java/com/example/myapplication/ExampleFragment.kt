package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ExampleFragment: Fragment() {
    companion object{
        private const val ARG_SECTON_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): ExampleFragment{
            return ExampleFragment().apply {
                arguments= Bundle().apply {
                    putInt(ARG_SECTON_NUMBER,sectionNumber)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_example,container,false)
        val textView: TextView = rootView.findViewById(R.id.section_label)
        val index = arguments?.getInt(ARG_SECTON_NUMBER)?:1
        textView.text = "Hello world from section: $index"
        return rootView
    }
}