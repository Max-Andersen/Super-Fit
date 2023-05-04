package com.example.superfitcompose.ui.auth.login

data class LoginViewState(
    val login: String = "",
    val password: String = "",

    val navigateToEnterPassword: Boolean = false,
    val navigateToRegister: Boolean = false,

    val errorMessage: String = ""

)
