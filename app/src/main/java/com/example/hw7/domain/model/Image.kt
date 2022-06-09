package com.example.hw7.domain.model

data class Image(
    val id: String,
    val owner: Owner,
    val dateCreated: Long,
    val sizes: List<ImageSize>
)
