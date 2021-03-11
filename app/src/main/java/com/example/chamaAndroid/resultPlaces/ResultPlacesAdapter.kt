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
    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [PlacesProperty] information.
     */
    class PlacesPropertyViewHolder(private var binding: ListViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: PlacesProperty) {
            binding.property = marsProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [PlacesProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<PlacesProperty>() {
        override fun areItemsTheSame(oldItem: PlacesProperty, newItem: PlacesProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlacesProperty, newItem: PlacesProperty): Boolean {
            return oldItem.placeId == newItem.placeId
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PlacesPropertyViewHolder {
        return PlacesPropertyViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PlacesPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [PlacesProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [PlacesProperty]
     */
    class OnClickListener(val clickListener: (marsProperty: PlacesProperty) -> Unit) {
        fun onClick(marsProperty:PlacesProperty) = clickListener(marsProperty)
    }
}