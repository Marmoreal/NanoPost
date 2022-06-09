package com.example.hw7.domain.model

data class Owner(
    val id: String,
    val username: String,
    val displayName: String?,
    val avatarUrl: String?,
    val subscribed: Boolean
)
