package com.ramgdev.chakulapap.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentLandingPageBinding

class LandingPageFragment : Fragment() {

    private lateinit var binding: FragmentLandingPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLandingPageBinding.inflate(inflater, container, false)

        binding.buttonCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_landingPageFragment_to_loginFragment)
        }

        binding.buttonAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_landingPageFragment_to_adminLoginFragment



            )
        }

        return binding.root
    }
}