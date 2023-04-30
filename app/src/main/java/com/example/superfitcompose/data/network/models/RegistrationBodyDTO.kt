package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.RegistrationBody
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationBodyDTO(
    val login: String,
    val password: Int
)


fun RegistrationBodyDTO.toDomain() = RegistrationBody(login, password)
