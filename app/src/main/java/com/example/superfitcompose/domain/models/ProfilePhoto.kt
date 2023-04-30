package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.ProfilePhotoDTO

data class ProfilePhoto(
    val id: String,
    val uploaded: Int,
)

fun ProfilePhoto.fromDomain() = ProfilePhotoDTO(id, uploaded)

