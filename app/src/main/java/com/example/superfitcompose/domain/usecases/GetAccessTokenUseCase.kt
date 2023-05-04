package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.RefreshToken
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class GetAccessTokenUseCase(private val refreshToken: RefreshToken) {
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    operator fun invoke() = authRepository.getAccessToken(refreshToken.fromDomain())
}