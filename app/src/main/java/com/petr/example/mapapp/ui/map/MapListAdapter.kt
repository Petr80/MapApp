package com.petr.example.mapapp.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.databinding.ListItemsBinding

class MapListAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MapItemViewHolder(ListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MapItemViewHolder).bind(item)
    }

    class MapItemViewHolder(private val binding: ListItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.item?.let { item ->
                    // navigateToDetailImage(item, view)
                }
            }
        }

        fun bind(items: Item) {
            binding.apply {
                item = items
                executePendingBindings()
            }
        }
    }
}

private class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}