package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.AuthResponseDTO

data class AuthResponse(
    val username: String,
    val refreshToken: String,
    val expired: Int
)


fun AuthResponse.fromDomain() = AuthResponseDTO(username, refreshToken, expired)
