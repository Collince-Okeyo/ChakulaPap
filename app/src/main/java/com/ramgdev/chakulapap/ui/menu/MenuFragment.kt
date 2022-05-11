package com.ramgdev.chakulapap.ui.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentMenuBinding
import com.ramgdev.chakulapap.model.MenuItems
import timber.log.Timber

@SuppressLint("RestrictedApi")
class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding
    private val args: MenuFragmentArgs by navArgs()
    private lateinit var menuItems: MenuItems
    private lateinit var databaseReference: DatabaseReference
    private val menuAdapter by lazy {
        com.ramgdev.chakulapap.adapter.MenuAdapter(com.ramgdev.chakulapap.adapter.MenuAdapter.OnClickListener{ item ->
            Toast.makeText(requireContext(), "${item.menuName}", Toast.LENGTH_SHORT).show()
            //binding.toolbarMenu.title = item.menuName
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
        showOrders()
        binding.toolbarMenu.title = "${args.hotelargs.hotelName} Hotel Menu"
        binding.toolbarMenu.setNavigationOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_dashboardFragment)
        })

        Glide.with(binding.imageViewPopularFood)
            .load(args.hotelargs.hotelImage)
            .into(binding.imageViewPopularFood)

        databaseReference = FirebaseDatabase.getInstance().reference

        getPopularDish()
        getMenus()
        return binding.root
    }

    private fun getMenus(){
        menuArrayList.clear()
        databaseReference.child("menus").child(args.hotelargs.hotelName.toString())
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Timber.d("Hotel: " + args.hotelargs.hotelName + " " + "onDataChange: " + snapshot)

                    if (snapshot.exists()) {
                        for (i in snapshot.children) {
                            val myMenuItems = i.getValue(MenuItems::class.java)
                            if (myMenuItems != null) {
                                //args.hotelargs.hotelMenu?.let { menuArrayList.add(it) }
                                menuArrayList.add(myMenuItems)
                                Timber.d("onDataChange: $menuArrayList")
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

    private fun getPopularDish(){
        databaseReference.child("menus").child("featured_meal").child(args.hotelargs.hotelName.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val result = snapshot.getValue(MenuItems::class.java)
                        binding.textViewPopularFood.text = result?.foodName
//                        binding.textViewPopularFoodPrice.text = result?.foodPrice
                        Glide.with(binding.imageViewPopularFood)
                            .load(result?.foodUri)
                            .into(binding.imageViewPopularFood)
                    }else{
                        Toast.makeText(requireContext(), "No Details", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "An error $error occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun showOrders(){

        val sharedPreferences = requireContext().getSharedPreferences("Admin", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isAdmin", false)) {
            binding.textViewPopularFood.visibility = VISIBLE
        }
    }

}