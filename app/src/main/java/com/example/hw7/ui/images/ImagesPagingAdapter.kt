package com.example.hw7.ui.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw7.databinding.ItemImagesListBinding
import com.example.hw7.domain.model.Image

class ImagesPagingAdapter :
    PagingDataAdapter<Image, ImagesPagingAdapter.ImageViewHolder>(ImageItemCallback) {

    private var onItemClick: ((Image) -> Unit)? = null

    fun setOnItemClick(callback: (Image) -> Unit) {
        onItemClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ImageViewHolder(
            ItemImagesListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ImageViewHolder(
        private val binding: ItemImagesListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.image.load(item.sizes.last().url)
            binding.image.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    private object ImageItemCallback : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}