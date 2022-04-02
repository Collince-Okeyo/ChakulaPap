package com.ramgdev.chakulapap.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.ramgdev.chakulapap.R

class ScreenOneFragment : Fragment() {
    private lateinit var buttonNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_screen_one, container, false)

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        buttonNext = view.findViewById(R.id.buttonNext1)

        buttonNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }
}