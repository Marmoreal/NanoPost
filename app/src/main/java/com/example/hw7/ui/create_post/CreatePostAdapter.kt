package com.example.hw7.ui.create_post

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw7.databinding.ItemCreatePostImagesListBinding

class CreatePostAdapter :
    ListAdapter<Uri, CreatePostAdapter.CreatePostAdapterViewHolder>(ImagesItemCallback) {

    private var onItemClick: ((Uri) -> Unit)? = null

    fun setOnItemClick(callback: (Uri) -> Unit) {
        onItemClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatePostAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return CreatePostAdapterViewHolder(
            ItemCreatePostImagesListBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CreatePostAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CreatePostAdapterViewHolder(
        private val binding: ItemCreatePostImagesListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            binding.picture.load(uri)

            binding.btnDeleteItem.setOnClickListener {
                onItemClick?.invoke(uri)
            }
        }

    }

    private object ImagesItemCallback : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }
}