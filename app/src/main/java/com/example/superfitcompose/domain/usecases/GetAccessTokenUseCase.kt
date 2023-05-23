package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.RefreshToken
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class GetAccessTokenUseCase(private val authRepositoryImpl: AuthRepositoryImpl) {
    operator fun invoke(refreshToken: RefreshToken) =
        authRepositoryImpl.getAccessToken(refreshToken.fromDomain())
}