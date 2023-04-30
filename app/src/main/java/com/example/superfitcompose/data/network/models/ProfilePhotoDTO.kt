package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.ProfilePhoto
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePhotoDTO(
    val id: String,
    val uploaded: Int,
)

fun ProfilePhotoDTO.toDomain() = ProfilePhoto(id, uploaded)

