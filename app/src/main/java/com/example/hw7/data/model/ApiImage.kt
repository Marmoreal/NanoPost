package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiImage(
    val id: String,
    val owner: ApiOwner,
    val dateCreated: Long,
    val sizes: List<ApiImageSize>
)
