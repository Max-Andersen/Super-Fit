package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class GetRefreshTokenUseCase(private val loginForm: AuthCredential) {
    private val authRepository: AuthRepository = AuthRepositoryImpl()

    operator fun invoke() = authRepository.getRefreshToken(loginForm.fromDomain())
}