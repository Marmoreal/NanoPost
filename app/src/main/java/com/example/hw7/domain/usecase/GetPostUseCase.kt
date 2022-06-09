package com.example.hw7.domain.usecase

import com.example.hw7.domain.model.Post
import com.example.hw7.domain.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId: String): Post {
        return postRepository.getPost(postId)
    }
}