package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.PhotoId
import kotlinx.serialization.Serializable

@Serializable
data class PhotoIdDTO(
    val id: String,
    val uploaded: Int,
)


fun PhotoIdDTO.toDomain() = PhotoId(id, uploaded)
