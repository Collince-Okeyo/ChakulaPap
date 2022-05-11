package com.ramgdev.chakulapap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramgdev.chakulapap.databinding.ConfirmedOrdersRowBinding
import com.ramgdev.chakulapap.model.Order

class ConfirmedOrdersAdapter() : ListAdapter<Order, ConfirmedOrdersAdapter.OrdersViewHolder>(MenuDiffUtil){

    object MenuDiffUtil: DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.paymentMode == newItem.paymentMode
        }
    }

    inner class OrdersViewHolder(private val binding: ConfirmedOrdersRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orders: Order?) {
            binding.foodNameTv.text = "Food Name ${orders?.foodName}"
            binding.orderPrice.text = "Price ${orders?.orderAmount}"
            binding.numberOfOrdersTv.text = "Number of Orders ${orders?.numberOfOrders}"
            binding.tableNumberTv.text = "Table Number ${orders?.tableNumber}"
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