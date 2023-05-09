package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.RefreshToken
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    @SerializedName("refresh_token") val refreshToken: String
)


fun RefreshTokenDTO.toDomain() = RefreshToken(refreshToken)
