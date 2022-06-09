package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.PreferencesRepository
import javax.inject.Inject

class AddUserAuthDataUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(token: String, userId: String) {
        preferencesRepository.addToken(token)
        preferencesRepository.addUserId(userId)
    }
}