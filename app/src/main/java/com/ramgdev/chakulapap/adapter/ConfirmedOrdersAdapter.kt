package com.ramgdev.chakulapap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramgdev.chakulapap.databinding.ConfirmedOrdersRowBinding
import com.ramgdev.chakulapap.model.ConOrders
import com.ramgdev.chakulapap.model.MenuItems
import com.ramgdev.chakulapap.model.Order

class ConfirmedOrdersAdapter() : ListAdapter<ConOrders, ConfirmedOrdersAdapter.OrdersViewHolder>(MenuDiffUtil){

    object MenuDiffUtil: DiffUtil.ItemCallback<ConOrders>() {
        override fun areItemsTheSame(oldItem: ConOrders, newItem: ConOrders): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ConOrders, newItem: ConOrders): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class OrdersViewHolder(private val binding: ConfirmedOrdersRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orders: ConOrders?) {
            binding.foodNameTv.text = "Food Name ${orders?.foodName}"
            binding.orderPrice.text = "Price ${orders?.customerName}"
            binding.orderPrice.text = "Price ${orders?.numberOfOrders}"
            binding.orderPrice.text = "Price ${orders?.orderPrice}"
            binding.orderPrice.text = "Price ${orders?.tableNumber}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmedOrdersAdapter.OrdersViewHolder {
        return OrdersViewHolder(ConfirmedOrdersRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ConfirmedOrdersAdapter.OrdersViewHolder, position: Int) {
        val orders = getItem(position)
        holder.bind(orders)
    }
}