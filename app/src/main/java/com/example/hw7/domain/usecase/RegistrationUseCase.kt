package com.example.hw7.domain.usecase

import com.example.hw7.data.model.TokenResponse
import com.example.hw7.domain.repository.AuthRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String, password: String) : TokenResponse {
        return authRepository.userRegistration(username,password)
    }
}