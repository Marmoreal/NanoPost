package com.example.hw7.domain.usecase

import com.example.hw7.domain.model.Post
import com.example.hw7.domain.repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(text: String?, array: List<ByteArray>?): Post {
        return postRepository.createPost(text, array)
    }
}