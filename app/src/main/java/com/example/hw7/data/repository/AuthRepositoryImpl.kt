package com.example.hw7.data.repository

import com.example.hw7.domain.AuthApiService
import com.example.hw7.data.model.AuthData
import com.example.hw7.data.model.CheckUsernameResponse
import com.example.hw7.data.model.TokenResponse
import com.example.hw7.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRepository {

    override suspend fun getCheckUsername(username: String): CheckUsernameResponse {
        return authApiService.getCheckUsername(username)
    }

    override suspend fun userAuthorization(username: String, password: String): TokenResponse {
        return authApiService.userAuthorisation(username, password)
    }

    override suspend fun userRegistration(username: String, password: String): TokenResponse {
        return authApiService.userRegistration(AuthData(username, password))
    }
}