package com.example.hw7.ui.post

import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.hw7.R
import com.example.hw7.databinding.FragmentPostBinding
import com.example.hw7.domain.model.Post
import com.example.hw7.ui.profile.ProfileFragment
import com.example.hw7.ui.profile.ProfileImagesAdapter
import com.example.hw7.ui.utils.formatDateStringFrom
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val imagesPagerAdapter = PostImagePagerAdapter()
    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPost(args.postId)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.showMenuLiveData.observe(viewLifecycleOwner) {
            binding.toolbar.inflateMenu(R.menu.post_menu)
        }

        viewModel.postLiveData.observe(viewLifecycleOwner) { post ->
            setPost(post)
            if (!post.images.isNullOrEmpty()) {
                imagesPagerAdapter.submitList(post.images)
            }

            binding.toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.deletePost -> {
                        viewModel.deletePost(post.id)
                        true
                    }
                    else -> false
                }
            }
        }

        viewModel.navigateLiveData.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun setPost(post: Post) {

        if (post.owner.avatarUrl.isNullOrBlank()) {
            binding.profileImage.setImageResource(R.drawable.ic_profile)
        } else {
            binding.profileImage.load(post.owner.avatarUrl)
        }
        binding.profileName.text = post.owner.displayName ?: post.owner.username
        binding.dateCreated.text = formatDateStringFrom(post.dateCreated, "MMM d, yyyy HH:mm:ss")

        binding.cardText.isVisible = !post.text.isNullOrBlank()
        if (binding.cardText.isVisible) {
            binding.cardText.text = post.text
        }

        binding.profileImage.setOnClickListener {
            findNavController().navigate(
                PostFragmentDirections.actionPostFragmentToProfileFragment(
                    post.owner.id
                )
            )
        }

        binding.viewPager.apply {
            adapter = imagesPagerAdapter

        }

        binding.circleIndicator.apply {
            setViewPager(binding.viewPager)
            if (post.images.size > 1) {
                createIndicators(post.images.size, 0)
            }
        }
    }
}