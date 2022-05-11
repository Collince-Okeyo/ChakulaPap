package com.ramgdev.chakulapap.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.adapter.ConfirmedOrdersAdapter
import com.ramgdev.chakulapap.databinding.FragmentConfirmedOrdersBinding

class ConfirmedOrdersFragment : Fragment() {

    private lateinit var binding: FragmentConfirmedOrdersBinding
    private val ordersAdapter by lazy { ConfirmedOrdersAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmedOrdersBinding.inflate(inflater, container, false)

        binding.order.showShimmerAdapter()



        return binding.root
    }
}