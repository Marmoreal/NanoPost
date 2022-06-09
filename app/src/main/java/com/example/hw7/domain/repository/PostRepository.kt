package com.example.hw7.domain.repository

import androidx.paging.PagingData
import com.example.hw7.data.model.ResultResponse
import com.example.hw7.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getFeed(): Flow<PagingData<Post>>
    suspend fun getPost(postId: String): Post
    suspend fun createPost(text: String?, list: List<ByteArray>?): Post
    suspend fun deletePost(postId: String): Boolean
}