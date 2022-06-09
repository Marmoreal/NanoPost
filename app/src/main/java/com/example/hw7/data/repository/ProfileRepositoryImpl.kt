package com.example.hw7.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.hw7.data.mappers.toImage
import com.example.hw7.data.mappers.toPost
import com.example.hw7.data.mappers.toProfile
import com.example.hw7.data.model.PushToken
import com.example.hw7.domain.NanoPostApiService
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.model.Post
import com.example.hw7.domain.model.Profile
import com.example.hw7.domain.repository.ProfileRepository
import com.example.hw7.paging.StringKeyedPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val nanoPostApiService: NanoPostApiService
) : ProfileRepository {

    override suspend fun getProfile(profileId: String): Profile {
        return nanoPostApiService.getProfile(profileId).toProfile()
    }

    override suspend fun getProfilePosts(
        profileId: String
    ): Flow<PagingData<Post>> {
        return Pager(PagingConfig(30, enablePlaceholders = false), "0") {
            StringKeyedPagingSource {
                nanoPostApiService.getProfilePosts(profileId, it.loadSize, it.key)
            }
        }.flow.map {
            it.map { apiPost ->
                apiPost.toPost()
            }
        }
    }

    override suspend fun getProfileImages(
        profileId: String
    ): Flow<PagingData<Image>> {
        return Pager(PagingConfig(30, enablePlaceholders = false), "0") {
            StringKeyedPagingSource {
                nanoPostApiService.getProfileImages(profileId, it.loadSize, it.key)
            }
        }.flow.map {
            it.map { apiImage ->
                apiImage.toImage()
            }
        }
    }

    override suspend fun subscribeToProfile(profileId: String): Boolean {
        return nanoPostApiService.subscribeToProfile(profileId).result
    }

    override suspend fun unsubscribeFromProfile(profileId: String): Boolean {
        return nanoPostApiService.unsubscribeFromProfile(profileId).result
    }

    override suspend fun setPushToken(pushToken: PushToken): Boolean {
        return nanoPostApiService.setPushToken(pushToken).result
    }

    override suspend fun deletePushToken(): Boolean {
        return nanoPostApiService.deletePushToken().result
    }
}