package com.example.hw7.domain.usecase

import com.example.hw7.domain.repository.PreferencesRepository
import javax.inject.Inject

class AddPushTokenUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(pushToken: String) {
        preferencesRepository.addPushToken(pushToken)
    }
}