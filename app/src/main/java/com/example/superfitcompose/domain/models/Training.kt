package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.TrainingDTO
import com.example.superfitcompose.data.network.models.TrainingType

data class Training(
    val date: String,
    val exercise: TrainingType,
    val repeatCount: Int
)

fun Training.fromDomain() = TrainingDTO(date, exercise, repeatCount)

