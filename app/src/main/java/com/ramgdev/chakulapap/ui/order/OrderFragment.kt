package com.ramgdev.chakulapap.ui.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentOrderBinding
import com.ramgdev.chakulapap.model.Order
import timber.log.Timber

class OrderFragment : Fragment() {
    private lateinit var order: Order
    private lateinit var binding : FragmentOrderBinding
    private var tableNo = 1
    private var foodQuantity = 1
    private val args: OrderFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false)

        val food_order = args.foodOrder

        binding.orderFoodName.text = food_order.menuName.toString()

        binding.orderFoodBtn.text = food_order.menuPrice.toString()

        var delimiter = "."
        val price = food_order.menuPrice.toString().split(delimiter)
        Timber.d("onCreateView: $price")

        Glide.with(binding.orderFoodPic)
            .load(food_order.menuImage)
            .into(binding.orderFoodPic)

        Glide.with(binding.orderHotelPic)
            .load(args.hotelDetails.hotelImage)
            .into(binding.orderHotelPic)

        binding.orderFoodTableNumberMinus.setOnClickListener {
            if (tableNo > 1) {
                tableNo -= 1
            }
            val tableNoMinus = tableNo
            binding.orderTableNumber.text = tableNoMinus.toString()
        }
        binding.orderFoodTableNumberAdd.setOnClickListener {
            tableNo += 1
            val tableNoAdd = tableNo
            binding.orderTableNumber.text = tableNoAdd.toString()
        }
        binding.orderFoodQuantityMinus.setOnClickListener {
            if (foodQuantity > 1) {
                foodQuantity -= 1
            }
            val toStr = foodQuantity
            binding.orderFoodQuantity.text = toStr.toString()
            binding.orderFoodBtn.text = "Pay Ksh." + (toStr * price[1].toInt()).toString().trim()
        }
        binding.orderFoodQuantityAdd.setOnClickListener {
            foodQuantity += 1
            val toStr = foodQuantity
            binding.orderFoodQuantity.text = toStr.toString()
            binding.orderFoodBtn.text = "Pay Ksh." + (toStr * price[1].toInt()).toString().trim()
        }
        binding.orderFoodBtn.setOnClickListener {
            ConfirmationCustomDialog().showsDialog
            order = Order(
                foodQuantity,
                tableNo.toString(),
                price[1].toInt(),
                food_order.menuName.toString(),
                food_order.accountType
            )

            val action = OrderFragmentDirections.actionOrderFragmentToConfirmationCustomDialogFragment2(order)
            findNavController().navigate(action)
        }
        return binding.root
    }

}