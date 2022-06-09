package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetPushTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    operator fun invoke(): String? {
        return preferencesRepository.getPushToken()
    }
}