package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.PreferencesRepository
import com.example.hw7.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteUserAuthDataUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke() {
        profileRepository.deletePushToken()
        preferencesRepository.deleteToken()
        preferencesRepository.deleteUserId()
    }
}