package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.Profile
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    val login: String,
)


fun ProfileDTO.toDomain() = Profile(login)
