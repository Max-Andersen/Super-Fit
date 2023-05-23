package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class GetRefreshTokenUseCase(private val authRepositoryImpl: AuthRepositoryImpl) {
    operator fun invoke(loginForm: AuthCredential) =
        authRepositoryImpl.getRefreshToken(loginForm.fromDomain())
}