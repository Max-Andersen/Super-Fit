package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.AuthCredential
import kotlinx.serialization.Serializable

@Serializable
data class AuthCredentialDTO(
    val login: String,
    val password: String
)

fun AuthCredentialDTO.toDomain() = AuthCredential(login, password)
