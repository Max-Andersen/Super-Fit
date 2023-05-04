package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.RefreshTokenDTO

data class RefreshToken(
    val refreshToken: String
)


fun RefreshToken.fromDomain() = RefreshTokenDTO(refreshToken)
