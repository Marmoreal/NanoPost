package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLike(
    val liked: Boolean,
    val likesCount: Int
)
