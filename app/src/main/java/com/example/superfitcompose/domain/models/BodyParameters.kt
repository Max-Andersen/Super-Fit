package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.BodyParametersDTO

data class BodyParameters(
    val weight: Int,
    val height: Int,
    val date: String
)


fun BodyParameters.fromDomain() = BodyParametersDTO(weight, height, date)
