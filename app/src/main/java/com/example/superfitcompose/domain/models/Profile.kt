package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.ProfileDTO

data class Profile(
    val login: String,
)

fun Profile.fromDomain() = ProfileDTO(login)

