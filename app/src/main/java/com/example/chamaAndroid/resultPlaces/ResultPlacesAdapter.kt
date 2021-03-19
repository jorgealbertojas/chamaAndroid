package com.example.chamaAndroid.resultPlaces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chamaAndroid.databinding.ListViewItemBinding
import com.example.chamaAndroid.network.PlacesProperty

class ResultPlacesAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<PlacesProperty, ResultPlacesAdapter.PlacesPropertyViewHolder>(DiffCallback) {

    class PlacesPropertyViewHolder(private var binding: ListViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: PlacesProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlacesProperty>() {
        override fun areItemsTheSame(oldItem: PlacesProperty, newItem: PlacesProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlacesProperty, newItem: PlacesProperty): Boolean {
            return oldItem.placeId == newItem.placeId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PlacesPropertyViewHolder {
        return PlacesPropertyViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PlacesPropertyViewHolder, position: Int) {
        val placesProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(placesProperty)
        }
        holder.bind(placesProperty)
    }

    class OnClickListener(val clickListener: (placesProperty: PlacesProperty) -> Unit) {
        fun onClick(placesProperty:PlacesProperty) = clickListener(placesProperty)
    }
}