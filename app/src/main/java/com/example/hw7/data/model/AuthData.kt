package com.example.hw7.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthData(
    val username: String,
    val password: String
)
