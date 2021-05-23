package com.petr.example.mapapp.ui.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petr.example.mapapp.data.Photo
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petr.example.mapapp.databinding.PhotoListItemBinding

class PhotoListAdapter : ListAdapter<Photo, RecyclerView.ViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoViewHolder(PhotoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = getItem(position)
        (holder as PhotoViewHolder).bind(photo)
    }

    class PhotoViewHolder(private val binding: PhotoListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.photo?.let { image ->
                    navigateToPhotoDetail(image, view)
                }
            }
        }

        private fun navigateToPhotoDetail(photo: Photo, view: View) {
            val direction = PhotoListFragmentDirections.actionNavigationPhotosToNavigationPhotoDetail(photo.photoId)
            view.findNavController().navigate(direction)
        }

        fun bind(photoItem: Photo) {
            with(binding){
                photo = photoItem
                executePendingBindings()
            }
        }
    }
}

private class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.photoId == newItem.photoId
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}
