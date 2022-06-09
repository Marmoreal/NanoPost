package com.example.hw7.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hw7.R
import com.example.hw7.databinding.FragmentImagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : Fragment() {

    private lateinit var binding: FragmentImagesBinding
    private val viewModel: ImagesViewModel by viewModels()
    private val imagesAdapter = ImagesPagingAdapter()
    private val args: ImagesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadData(args.profileId)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadData(args.profileId)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.imagesList.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = imagesAdapter.apply {
                setOnItemClick { image ->
                    findNavController().navigate(
                        ImagesFragmentDirections.actionImagesFragmentToImageFragment(image.id)
                    )
                }
            }
        }

        viewModel.imagesLiveData.observe(viewLifecycleOwner) {
            imagesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}