package com.example.hw7.domain

import com.example.hw7.data.model.AuthData
import com.example.hw7.data.model.CheckUsernameResponse
import com.example.hw7.data.model.TokenResponse
import retrofit2.http.*

interface AuthApiService {
    @GET("checkUsername")
    suspend fun getCheckUsername(
        @Query("username") username: String
    ): CheckUsernameResponse

    @GET("login")
    suspend fun userAuthorisation(
        @Query("username") username: String,
        @Query("password") password: String
    ): TokenResponse

    @POST("register")
    suspend fun userRegistration(
        @Body authData: AuthData
    ): TokenResponse
}