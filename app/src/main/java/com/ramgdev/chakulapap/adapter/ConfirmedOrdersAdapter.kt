package com.ramgdev.chakulapap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramgdev.chakulapap.databinding.ConfirmedOrdersRowBinding
import com.ramgdev.chakulapap.model.MenuItems
import com.ramgdev.chakulapap.model.Order

class ConfirmedOrdersAdapter() : ListAdapter<MenuItems, ConfirmedOrdersAdapter.OrdersViewHolder>(MenuDiffUtil){

    object MenuDiffUtil: DiffUtil.ItemCallback<MenuItems>() {
        override fun areItemsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class OrdersViewHolder(private val binding: ConfirmedOrdersRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(orders: MenuItems?) {
            binding.foodNameTv.text = "Food Name ${orders?.foodName}"
            binding.orderPrice.text = "Price ${orders?.menuPrice}"
            /*binding.numberOfOrdersTv.text = "Number of Orders ${orders?.numberOfOrders}"
            binding.tableNumberTv.text = "Table Number ${orders?.tableNumber}"*/
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