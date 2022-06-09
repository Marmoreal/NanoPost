package com.example.hw7.domain.repository


interface ImageRepository {
    suspend fun getImage(imageId: String): com.example.hw7.domain.model.Image
    suspend fun deleteImage(imageId: String): Boolean
}