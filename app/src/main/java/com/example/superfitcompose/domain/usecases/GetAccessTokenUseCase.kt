package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.RefreshToken
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class GetAccessTokenUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(refreshToken: RefreshToken) =
        authRepository.getAccessToken(refreshToken.fromDomain())
}