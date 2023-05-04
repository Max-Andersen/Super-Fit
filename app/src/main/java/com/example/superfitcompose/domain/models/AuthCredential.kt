package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.AuthCredentialDTO

data class AuthCredential(
    val login: String,
    val password: String
)

fun AuthCredential.fromDomain() = AuthCredentialDTO(login, password)
