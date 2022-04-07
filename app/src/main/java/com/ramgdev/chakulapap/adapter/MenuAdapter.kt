package com.ramgdev.chakulapap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.chakulapap.databinding.MenuRowBinding
import com.ramgdev.chakulapap.model.MenuItems

class MenuAdapter(private val onClickListener: OnClickListener) : ListAdapter<MenuItems, MenuAdapter.MenuViewHolder>(MenuDiffUtil){

    object MenuDiffUtil: DiffUtil.ItemCallback<MenuItems>() {
        override fun areItemsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MenuItems, newItem: MenuItems): Boolean {
            return newItem.id == newItem.id
        }
    }

    inner class MenuViewHolder(private val binding: MenuRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: MenuItems?) {
            Glide.with(binding.imageViewFood)
                .load(menu?.menuImage)
                .centerCrop()
                .into(binding.imageViewFood)
            binding.textViewFoodName.text = menu?.menuName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(MenuRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = getItem(position)
        holder.bind(menu)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(menu)
        }
    }

    class OnClickListener(val clickListener: (menuItems: MenuItems) -> Unit) {
        fun onClick(menuItems: MenuItems) = clickListener(menuItems)
    }

}