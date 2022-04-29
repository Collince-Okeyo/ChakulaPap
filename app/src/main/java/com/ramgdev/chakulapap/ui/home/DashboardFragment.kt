package com.ramgdev.chakulapap.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.*
import com.ramgdev.chakulapap.adapter.HotelAdapter
import com.ramgdev.chakulapap.adapter.MenuAdapter
import com.ramgdev.chakulapap.adapter.PopularHotelAdapter
import com.ramgdev.chakulapap.databinding.FragmentDashboardBinding
import com.ramgdev.chakulapap.model.HotelModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var databaseReference: DatabaseReference

    private val hotelsArrayList: ArrayList<HotelModel> = ArrayList<HotelModel>()
    private val popularHotelsArrayList: ArrayList<HotelModel> = ArrayList<HotelModel>()

    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var popularHotelAdapter: PopularHotelAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        binding.hotelsRecycler.showShimmerAdapter()
        binding.popularHotelRecycler.showShimmerAdapter()
        databaseReference = FirebaseDatabase.getInstance().reference

        hotelAdapter = HotelAdapter(HotelAdapter.OnClickListener { hotelModel ->
            val action = DashboardFragmentDirections.actionDashboardFragmentToMenuFragment(hotelModel)
            findNavController().navigate(action)
        })

        popularHotelAdapter = PopularHotelAdapter(PopularHotelAdapter.OnClickListener{ hotelModel ->
            val action = DashboardFragmentDirections.actionDashboardFragmentToMenuFragment(hotelModel)
            findNavController().navigate(action)
        })

        getPopularHotels()
        getHotels()
        return binding.root
    }

    private fun getPopularHotels(){
        popularHotelsArrayList.clear()
        databaseReference.child("popular_hotels").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val hotelModel: HotelModel? = i.getValue(HotelModel::class.java)
                        if (hotelModel != null){
                            popularHotelsArrayList.add(hotelModel)
                        }
                    }
                    popularHotelAdapter.submitList(popularHotelsArrayList)
                    binding.popularHotelRecycler.adapter = popularHotelAdapter
                    binding.popularHotelRecycler.hideShimmerAdapter()
                }else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getHotels(){
        hotelsArrayList.clear()
        databaseReference.child("hotels").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (i in snapshot.children){
                        val hotelModel: HotelModel? = i.getValue(HotelModel::class.java)
                        if (hotelModel != null){
                            hotelsArrayList.add(hotelModel)
                        }
                    }
                    hotelAdapter.submitList(hotelsArrayList)
                    binding.hotelsRecycler.adapter = hotelAdapter
                    binding.hotelsRecycler.hideShimmerAdapter()
                }else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}