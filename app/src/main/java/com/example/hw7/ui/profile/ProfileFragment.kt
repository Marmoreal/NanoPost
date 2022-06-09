package com.example.hw7.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.hw7.R
import com.example.hw7.databinding.FragmentProfileBinding
import com.example.hw7.domain.model.Profile
import com.example.hw7.ui.feed.PostPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()
    private val postAdapter = PostPagingAdapter()
    private val imagesAdapter = ProfileImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData(args.profileId)

        binding.imagesList.apply {
            adapter = imagesAdapter.apply {
                setOnItemClick { image ->
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToImageFragment(
                            image.id
                        )
                    )
                }
            }

        }

        viewModel.profileLiveData.observe(viewLifecycleOwner) { profile ->
            setProfile(profile)

            if (profile.id != args.profileId) {
                if (!binding.toolbar.menu.hasVisibleItems()) {
                    binding.toolbar.inflateMenu(R.menu.profile_menu)
                }
                binding.toolbar.setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                        R.id.logout -> {
                            viewModel.onClickLogout()
                            true
                        }
                        else -> false
                    }
                }

                binding.btnSubscribeOrEdit.setOnClickListener {
                    // для теста
                    Toast.makeText(requireContext(), "it doesn't work yet..", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            if (profile.id == args.profileId) {
                binding.fab.isVisible = false
                binding.btnSubscribeOrEdit.apply {
                    when (profile.subscribed) {
                        true -> setText(R.string.unsubscribe)
                        false -> setText(R.string.subscribe)
                    }
                }
            }

            binding.imagesCard.isVisible = !profile.images.isNullOrEmpty()
            imagesAdapter.submitList(profile.images)

            binding.btnToGallery.setOnClickListener {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToImagesFragment(profile.id)
                )
            }
        }

        viewModel.logoutLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
        }

        viewModel.btnSubscribeLiveData.observe(viewLifecycleOwner) { subscribed ->
            binding.btnSubscribeOrEdit.apply {
                when (subscribed) {
                    true -> setText(R.string.unsubscribe)
                    false -> setText(R.string.subscribe)
                }
                setOnClickListener {
                    viewModel.onClickSubscribe()
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadData(args.profileId)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.feedList.apply {
            adapter = postAdapter.apply {
                setOnItemClick {
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToPostFragment(it.id)
                    )
                }
            }
        }

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            postAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCreatePostFragment())
        }
    }

    private fun setProfile(profile: Profile) {

        if (profile.avatarSmall.isNullOrBlank()) {
            binding.profileImage.setImageResource(R.drawable.ic_profile)
        } else {
            binding.profileImage.load(profile.avatarSmall)
        }
        binding.profileName.text = profile.displayName ?: profile.username
        binding.profileBio.text = profile.bio
        binding.imagesCount.text = profile.imagesCount.toString()
        binding.subscribersCount.text = profile.subscribersCount.toString()
        binding.postsCount.text = profile.postsCount.toString()
    }
}