package com.petr.example.mapapp.ui.maplist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.databinding.ListItemsImageBinding

class ImageListAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(ListItemsImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ItemViewHolder).bind(item)
    }

    class ItemViewHolder(private val binding: ListItemsImageBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.item?.let { item ->
/*                    navigateToDetail(item, view)*/
                }
            }
        }

        private fun navigateToDetail(item: Item, view: View) {
            val action = ItemsListFragmentDirections.actionNavigationDashboardToNavigationDetail(item.itemId)
            view.findNavController().navigate(action)
        }

        fun bind(items: Item) {
            binding.apply {
                item = items
                executePendingBindings()
            }
        }
    }
}

private class ImageDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}