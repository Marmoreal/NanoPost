package com.example.hw7.ui.create_post

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hw7.R
import com.example.hw7.databinding.FragmentCreatePostBinding
import com.example.hw7.service.PostCreateService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding
    private val viewModel: CreatePostViewModel by viewModels()
    private val imagesAdapter = CreatePostAdapter()

    private val getGalleryImageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { it ->
                viewModel.addToList(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.createPostLiveData.observe(viewLifecycleOwner) {
            requireContext().startService(
                PostCreateService.newIntent(
                    requireContext(),
                    it.first,
                    it.second
                )
            )
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.createPost -> {
                    viewModel.onClickCreatePost(binding.postText.text.toString())
                    true
                }
                else -> false
            }
        }

        binding.btnAddImage.setOnClickListener {
            getGalleryImageActivityResultLauncher.launch("image/*")
        }

        binding.recycler.apply {
            adapter = imagesAdapter.apply {
                setOnItemClick {
                    viewModel.deleteFromList(it)
                }
            }
        }

        viewModel.imagesUriLiveData.observe(viewLifecycleOwner) { list ->
            if (list.size == 4) {
                binding.btnAddImage.isVisible = false
            }
            imagesAdapter.submitList(list)
        }
    }
}