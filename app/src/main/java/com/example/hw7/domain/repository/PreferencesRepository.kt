package com.example.hw7.domain.repository

interface PreferencesRepository {
    fun getToken(): String?
    fun addToken(token: String)
    fun deleteToken()
    fun getUserId(): String?
    fun addUserId(userId: String)
    fun deleteUserId()
    fun addPushToken(pushToken: String)
    fun getPushToken(): String?
    fun deletePushToken()
}