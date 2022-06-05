package com.ramgdev.chakulapap.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.adapter.ConfirmedOrdersAdapter
import com.ramgdev.chakulapap.databinding.FragmentConfirmedOrdersBinding
import com.ramgdev.chakulapap.model.ConOrders
import com.ramgdev.chakulapap.model.MenuItems
import timber.log.Timber

class ConfirmedOrdersFragment : Fragment() {

    private lateinit var binding: FragmentConfirmedOrdersBinding
    private val ordersAdapter by lazy { ConfirmedOrdersAdapter() }
    private lateinit var databaseReference: DatabaseReference

    private val ordersArrayList: ArrayList<ConOrders> = ArrayList<ConOrders>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmedOrdersBinding.inflate(inflater, container, false)

        databaseReference = FirebaseDatabase.getInstance().reference

        return binding.root
    }

    /*private fun getConfirmedMenus(){
        databaseReference.child("orders").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val orders = i.getValue(ConOrders::class.java)
                        if (orders != null) {
                            ordersArrayList.add(orders)
                            Timber.d("onDataChange: $ordersArrayList")
                        }
                    }
                    ordersAdapter.submitList(ordersArrayList)
                    binding.order.adapter = ordersAdapter
                    binding.order.hideShimmerAdapter()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Menu items do not exist",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }*/
}