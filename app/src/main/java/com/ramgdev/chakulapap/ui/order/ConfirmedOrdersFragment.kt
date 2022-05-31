package com.ramgdev.chakulapap.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.adapter.ConfirmedOrdersAdapter
import com.ramgdev.chakulapap.databinding.FragmentConfirmedOrdersBinding

class ConfirmedOrdersFragment : Fragment() {

    private lateinit var binding: FragmentConfirmedOrdersBinding
    private val ordersAdapter by lazy { ConfirmedOrdersAdapter() }
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmedOrdersBinding.inflate(inflater, container, false)
        binding.order.showShimmerAdapter()

        databaseReference = FirebaseDatabase.getInstance().reference


        return binding.root
    }

    private fun getConfirmedMenus(){

    }
}