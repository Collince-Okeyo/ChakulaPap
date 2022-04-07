package com.ramgdev.chakulapap.ui.menu

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.*
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentMenuBinding
import com.ramgdev.chakulapap.model.MenuItems

@SuppressLint("RestrictedApi")
class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val args: MenuFragmentArgs by navArgs()
    private lateinit var menuItems: MenuItems
    private lateinit var databaseReference: DatabaseReference
    private val menuAdapter by lazy {
        com.ramgdev.chakulapap.adapter.MenuAdapter(com.ramgdev.chakulapap.adapter.MenuAdapter.OnClickListener{ item ->
            Toast.makeText(requireContext(), "${item.menuName}", Toast.LENGTH_SHORT).show()
            binding.toolbarMenu.title = item.menuName
            val action = MenuFragmentDirections.actionMenuFragmentToOrderFragment(item, args.hotelargs)
            findNavController().navigate(action)
        })
    }

    private val menuArrayList: ArrayList<MenuItems> = ArrayList<MenuItems>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMenuBinding.inflate(inflater, container, false)

        binding.menuRecycler.showShimmerAdapter()

        binding.toolbarMenu.setNavigationOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_dashboardFragment)
        })

        databaseReference = FirebaseDatabase.getInstance().reference

        getMenus()
        return binding.root
    }

    private fun getMenus(){
        databaseReference.child("menus").child(args.hotelargs.hotelName.toString())
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "Hotel: ${args.hotelargs.hotelName} "+"onDataChange: ${snapshot}")

                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            val myMenuItems = i.getValue(MenuItems::class.java)
                            if (myMenuItems != null){
                                //args.hotelargs.hotelMenu?.let { menuArrayList.add(it) }
                                menuArrayList.add(myMenuItems)
                                Log.d(TAG, "onDataChange: $menuArrayList")
                            }
                        }
                        menuAdapter.submitList(menuArrayList)
                        binding.menuRecycler.adapter = menuAdapter
                        binding.menuRecycler.hideShimmerAdapter()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Menu items do not exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "An error $error occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}