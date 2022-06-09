package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.ProfileRepository
import javax.inject.Inject

class UnsubscribeUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    suspend operator fun invoke(profileId: String): Boolean {
        return profileRepository.unsubscribeFromProfile(profileId)
    }
}