package com.example.hw7.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw7.R
import com.example.hw7.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    private val postAdapter = PostPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPosts()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadPosts()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.feedList.apply {
            adapter = postAdapter.apply {
                setOnItemClick { post ->
                    findNavController().navigate(
                        FeedFragmentDirections.actionFeedFragmentToPostFragment(post.id)
                    )
                }
                setOnImageProfileClick { profileId ->
                    findNavController().navigate(
                        FeedFragmentDirections.actionFeedFragmentToProfileFragment(profileId)
                    )
                }
            }
        }

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            postAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                FeedFragmentDirections.actionFeedFragmentToCreatePostFragment()
            )
        }
    }
}