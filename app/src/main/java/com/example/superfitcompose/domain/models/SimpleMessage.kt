package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.SimpleMessageDTO

data class SimpleMessage(
    val message: String
)


fun SimpleMessage.fromDomain() = SimpleMessageDTO(message)
