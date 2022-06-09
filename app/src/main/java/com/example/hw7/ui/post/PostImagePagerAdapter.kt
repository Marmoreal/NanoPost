package com.example.hw7.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw7.databinding.ItemPostImagesListBinding
import com.example.hw7.domain.model.Image

class PostImagePagerAdapter(
) : ListAdapter<Image, PostImagePagerAdapter.PostImagesViewHolder>(ImagesItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PostImagesViewHolder(
            ItemPostImagesListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostImagesViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class PostImagesViewHolder(
        private val binding: ItemPostImagesListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.picture.isVisible = !item.sizes.isEmpty()
            binding.picture.load(item.sizes.first().url)
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