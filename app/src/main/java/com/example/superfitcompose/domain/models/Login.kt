package com.example.superfitcompose.domain.models

import com.example.superfitcompose.data.network.models.LoginDTO

data class Login(
    val login: String
)


fun Login.fromDomain() = LoginDTO(login)
