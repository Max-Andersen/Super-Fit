package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.AuthResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    val username: String,
    @SerialName("refresh_token") val refreshToken: String,
    val expired: Int
)

fun AuthResponseDTO.toDomain() = AuthResponse(username, refreshToken, expired)

