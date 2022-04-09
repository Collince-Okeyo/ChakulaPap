package com.ramgdev.chakulapap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdev.chakulapap.databinding.HotelsRowBinding
import com.ramgdev.chakulapap.model.HotelModel

class PopularHotelAdapter (private val onClickListener: OnClickListener) : ListAdapter<HotelModel, PopularHotelAdapter.HotelViewHolder>(PopularHotelsDiffUtil) {
    object PopularHotelsDiffUtil: DiffUtil.ItemCallback<HotelModel>() {
        override fun areItemsTheSame(oldItem: HotelModel, newItem: HotelModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HotelModel, newItem: HotelModel): Boolean {
            return oldItem.hotelId == newItem.hotelId
        }
    }

    inner class HotelViewHolder(private val binding: HotelsRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HotelModel?) {
            binding.hotelName.text = item?.hotelName
            Glide.with(binding.hotelImage)
                .load(item?.hotelImage)
                .into(binding.
                hotelImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(HotelsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    class OnClickListener(val clickListener: (hotelModel: HotelModel) -> Unit) {
        fun onClick(hotelModel: HotelModel) = clickListener(hotelModel)
    }
}