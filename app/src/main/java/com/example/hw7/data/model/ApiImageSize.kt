package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiImageSize(
    val width: Int,
    val height: Int,
    val url: String
)