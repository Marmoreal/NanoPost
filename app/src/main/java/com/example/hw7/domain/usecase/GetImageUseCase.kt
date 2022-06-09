package com.example.hw7.domain.usecase

import com.example.hw7.domain.model.Image
import com.example.hw7.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(imageId:String): Image {
        return imageRepository.getImage(imageId)
    }
}