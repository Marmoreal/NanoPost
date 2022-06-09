package com.example.hw7.domain.model

data class Post(
    val id: String,
    val text: String?,
    val owner: Owner,
    val dateCreated: Long,
    val images: List<Image>,
    val likes: Like
)