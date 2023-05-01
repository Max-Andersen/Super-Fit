package com.example.superfitcompose.ui.auth

data class AuthViewState(
    val login: String = "",
    val password: String = "",

    val username: String = "",
    val email: String = "",
    val code: String = "",
    val repeatCode: String = "",

    val enterUserName: Boolean = true,
    val enterPassword: Boolean = false,
    val register: Boolean = false
)
