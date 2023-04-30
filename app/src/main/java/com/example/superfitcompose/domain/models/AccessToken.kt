package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.AccessTokenDTO

data class AccessToken(
    val accessToken: String,
    val expired: Int
)


fun AccessToken.fromDomain() = AccessTokenDTO(accessToken, expired)


