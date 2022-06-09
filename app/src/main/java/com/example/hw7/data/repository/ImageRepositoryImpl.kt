package com.example.hw7.data.repository

import com.example.hw7.data.mappers.toImage
import com.example.hw7.domain.NanoPostApiService
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val nanoPostApiService: NanoPostApiService
) : ImageRepository {

    override suspend fun getImage(imageId: String): Image {
        return nanoPostApiService.getImage(imageId).toImage()
    }

    override suspend fun deleteImage(imageId: String): Boolean {
        return nanoPostApiService.deleteImage(imageId).result
    }
}