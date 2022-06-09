package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResponse(
    val token: String,
    val userId: String
)
