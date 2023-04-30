package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.Training
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDTO(
    val date: String,
    val exercise: TrainingType,
    val repeatCount: Int
)


fun TrainingDTO.toDomain() = Training(date, exercise, repeatCount)
