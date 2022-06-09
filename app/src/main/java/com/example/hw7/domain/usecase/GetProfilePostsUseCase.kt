package com.example.hw7.domain.usecase

import androidx.paging.PagingData
import com.example.hw7.domain.model.Image
import com.example.hw7.domain.model.Post
import com.example.hw7.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilePostsUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(profileId: String): Flow<PagingData<Post>> {
        return profileRepository.getProfilePosts(profileId)
    }
}