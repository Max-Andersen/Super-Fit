package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.AuthResponse
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    val username: String,
    val expired: Int,
    @SerializedName("refresh_token") val refreshToken: String,
)

fun AuthResponseDTO.toDomain() = AuthResponse(username)

