package com.example.hw7.domain.repository

import com.example.hw7.data.model.CheckUsernameResponse
import com.example.hw7.data.model.TokenResponse

interface AuthRepository {
    suspend fun getCheckUsername(username: String): CheckUsernameResponse
    suspend fun userAuthorization(username: String, password: String): TokenResponse
    suspend fun userRegistration(username: String, password: String): TokenResponse
}