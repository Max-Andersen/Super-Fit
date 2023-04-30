package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.Login
import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val login: String
)


fun LoginDTO.toDomain() = Login(login)
