package com.ramgdev.chakulapap.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.ui.onboarding.adapter.ViewPagerAdapter

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            ScreenOneFragment(),
            ScreenTwoFragment(),
            ScreenThreeFragment()
        )

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        viewPager.adapter = adapter

        return view
    }
}