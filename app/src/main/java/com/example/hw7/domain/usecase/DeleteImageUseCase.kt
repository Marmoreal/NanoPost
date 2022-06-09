package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(imageId: String): Boolean {
        return imageRepository.deleteImage(imageId)
    }
}