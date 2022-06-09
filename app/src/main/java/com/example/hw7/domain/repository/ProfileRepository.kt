package com.example.hw7.domain.repository

import androidx.paging.PagingData
import com.example.hw7.data.model.PushToken
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.model.Post
import com.example.hw7.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(profileId: String): Profile
    suspend fun getProfilePosts(profileId: String): Flow<PagingData<Post>>
    suspend fun getProfileImages(profileId: String): Flow<PagingData<Image>>
    suspend fun subscribeToProfile(profileId: String): Boolean
    suspend fun unsubscribeFromProfile(profileId: String): Boolean
    suspend fun setPushToken(pushToken: PushToken): Boolean
    suspend fun deletePushToken(): Boolean
}