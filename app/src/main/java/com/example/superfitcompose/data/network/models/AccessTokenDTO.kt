package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.AccessToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDTO(
    @SerialName("access_token") val accessToken: String,
    val expired: Int
)

fun AccessTokenDTO.toDomain() = AccessToken(accessToken, expired)
