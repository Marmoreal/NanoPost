package com.example.hw7.domain.usecase

import com.example.hw7.data.model.CheckUsernameResponse
import com.example.hw7.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String): CheckUsernameResponse {
        return authRepository.getCheckUsername(username)
    }
}