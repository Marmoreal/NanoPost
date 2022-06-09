package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushToken(
    val pushToken: String
)