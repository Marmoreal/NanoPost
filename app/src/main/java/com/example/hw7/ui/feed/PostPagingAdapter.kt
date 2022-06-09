package com.example.hw7.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw7.R
import com.example.hw7.domain.model.Post
import com.example.hw7.databinding.CardViewBinding
import com.example.hw7.ui.post.PostImagePagerAdapter
import com.example.hw7.ui.utils.formatDateStringFrom

class PostPagingAdapter :
    PagingDataAdapter<Post, PostPagingAdapter.PostViewHolder>(PostItemCallback) {

    private var onItemClick: ((Post) -> Unit)? = null

    fun setOnItemClick(callback: (Post) -> Unit) {
        onItemClick = callback
    }

    private var onImageProfileClick: ((String) -> Unit)? = null

    fun setOnImageProfileClick(callback: (String) -> Unit) {
        onImageProfileClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PostViewHolder(
            CardViewBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PostViewHolder(
        private val binding: CardViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imagesPagerAdapter = PostImagePagerAdapter()

        fun bind(item: Post) {

            if (item.owner.avatarUrl.isNullOrBlank()) {
                binding.profileImage.setImageResource(R.drawable.ic_profile)
            } else {
                binding.profileImage.load(item.owner.avatarUrl)
            }
            binding.profileName.text = item.owner.displayName ?: item.owner.username
            binding.dateCreated.text = formatDateStringFrom(item.dateCreated, "MMM d, yyyy HH:mm:ss")
            binding.cardText.isVisible = !item.text.isNullOrBlank()
            binding.cardText.text = item.text

            imagesPagerAdapter.submitList(item.images)

            binding.viewPager.isVisible = !item.images.isNullOrEmpty()
            if (binding.viewPager.isVisible) {
                binding.cardText.maxLines = 4

                binding.viewPager.apply {
                    adapter = imagesPagerAdapter
                }
            }

            binding.circleIndicator.apply {
                setViewPager(binding.viewPager)
                createIndicators(item.images.size, 0)
                if (item.images.size < 2) {
                    createIndicators(0,0)
                }
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }

            binding.profileImage.setOnClickListener {
                onImageProfileClick?.invoke(item.owner.id)
            }
        }
    }

    private object PostItemCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}