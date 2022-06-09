package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiPost(
    val id:String,
    val owner: ApiOwner,
    val dateCreated: Long,
    val text: String?,
    val images: List<ApiImage>,
    val likes: ApiLike
)
