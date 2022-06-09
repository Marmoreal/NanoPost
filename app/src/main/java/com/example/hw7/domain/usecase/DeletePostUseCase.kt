package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.PostRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(postId: String): Boolean {
        return postRepository.deletePost(postId)
    }
}