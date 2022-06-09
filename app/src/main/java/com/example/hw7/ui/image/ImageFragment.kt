package com.example.hw7.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.hw7.R
import com.example.hw7.databinding.FragmentImageBinding
import com.example.hw7.domain.model.Image
import com.example.hw7.ui.utils.formatDateStringFrom
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private val viewModel: ImageViewModel by viewModels()
    private val args: ImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadImage(args.imageId)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.showMenuLiveData.observe(viewLifecycleOwner) {
            binding.toolbar.inflateMenu(R.menu.image_menu)
        }

        viewModel.imageLiveData.observe(viewLifecycleOwner) { image ->
            setImage(image)

            binding.toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.deleteImage -> {
                        viewModel.deleteImage(image.id)
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

    private fun setImage(image: Image) {

        binding.image.load(image.sizes.first().url)

        if (image.owner.avatarUrl.isNullOrBlank()) {
            binding.profileImage.setImageResource(R.drawable.ic_profile)
        } else {
            binding.profileImage.load(image.owner.avatarUrl)
        }

        binding.profileName.text = image.owner.displayName ?: image.owner.username
        binding.dateCreated.text = formatDateStringFrom(image.dateCreated,"MMM d, yyyy HH:mm:ss")
    }
}