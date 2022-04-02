package com.ramgdev.chakulapap.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentScreenThreeBinding

class ScreenThreeFragment : Fragment() {

    private lateinit var binding: FragmentScreenThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenThreeBinding.inflate(inflater, container, false)

        binding.buttonFinish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_dashboardFragment)
            onBoardingFinished()
        }

        return binding.root
    }

    private fun onBoardingFinished() {
        val sharedPreferences = requireContext().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}