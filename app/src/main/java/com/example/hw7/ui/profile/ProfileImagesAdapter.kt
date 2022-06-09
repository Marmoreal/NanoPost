package com.example.hw7.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw7.databinding.ItemProfileImagesListBinding
import com.example.hw7.domain.model.Image

class ProfileImagesAdapter : ListAdapter<Image, ProfileImagesAdapter.ProfileImagesViewHolder>(ImagesItemCallback) {

    private var onItemClick: ((Image) -> Unit)? = null

    fun setOnItemClick(callback: (Image) -> Unit) {
        onItemClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ProfileImagesViewHolder(
            ItemProfileImagesListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileImagesViewHolder(
        private val binding: ItemProfileImagesListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.picture.isVisible = !item.sizes.isEmpty()
            binding.picture.load(item.sizes.last().url)

            binding.picture.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }

    }

    private object ImagesItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}