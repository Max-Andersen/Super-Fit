package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.RegistrationBodyDTO

data class RegistrationBody(
    val login: String,
    val password: Int
)


fun RegistrationBody.fromDomain() = RegistrationBodyDTO(login, password)

