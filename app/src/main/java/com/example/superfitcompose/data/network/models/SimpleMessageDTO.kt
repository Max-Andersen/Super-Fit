package com.example.superfitcompose.data.network.models

import com.example.superfitcompose.domain.models.SimpleMessage
import kotlinx.serialization.Serializable

@Serializable
data class SimpleMessageDTO(
    val message: String
)


fun SimpleMessageDTO.toDomain() = SimpleMessage(message)
