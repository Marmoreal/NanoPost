package com.example.hw7.domain.usecase

import com.example.hw7.data.model.PushToken
import com.example.hw7.domain.repository.ProfileRepository
import javax.inject.Inject

class SetPushTokenUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke(pushToken: String){
        profileRepository.setPushToken(PushToken(pushToken))
    }
}