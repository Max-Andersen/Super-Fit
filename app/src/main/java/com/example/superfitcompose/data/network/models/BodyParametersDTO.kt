package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.BodyParameters
import kotlinx.serialization.Serializable

@Serializable
data class BodyParametersDTO(
    val weight: Int,
    val height: Int,
    val date: String
)


fun BodyParametersDTO.toDomain() = BodyParameters(weight, height, date)
