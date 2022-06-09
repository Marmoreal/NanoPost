package com.example.hw7.domain

import com.example.hw7.data.PagedDataResponse
import com.example.hw7.data.model.*
import hilt_aggregated_deps._com_example_hw7_ui_MainActivity_GeneratedInjector
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface NanoPostApiService {
    // profile requests
    @GET("profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String
    ): ApiProfile

    @PUT("profile/{profileId}/subscribe")
    suspend fun subscribeToProfile(
        @Path("profileId") profileId: String
    ): ResultResponse

    @DELETE("profile/{profileId}/subscribe")
    suspend fun unsubscribeFromProfile(
        @Path("profileId") profileId: String
    ): ResultResponse

    @GET("posts/{profileId}")
    suspend fun getProfilePosts(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @GET("images/{profileId}")
    suspend fun getProfileImages(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiImage>

    //pushToken requests
    @PUT("profile/pushToken")
    suspend fun setPushToken(
        @Body pushToken: PushToken
    ): ResultResponse

    @DELETE("profile/pushToken")
    suspend fun deletePushToken(): ResultResponse

    //posts requests
    @GET("feed")
    suspend fun getFeed(
        @Query("count") count: Int,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @GET("post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String
    ): ApiPost

    @PUT("post")
    @Multipart
    suspend fun createPost(
        @Part("text") text: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?
    ): ApiPost

    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: String
    ): ResultResponse

    @GET("image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String
    ): ApiImage

    @DELETE("image/{imageId}")
    suspend fun deleteImage(
        @Path("imageId") imageId: String
    ): ResultResponse
}