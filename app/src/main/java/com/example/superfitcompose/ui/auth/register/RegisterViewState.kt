package com.example.superfitcompose.ui.auth.register

data class RegisterViewState (
    val username: String = "",
    val email: String = "",
    val code: String = "",
    val codeConfirmation: String = "",

    val errorMessage: String = "",
    val navigateToLogin: Boolean = false,
    val navigateMainScreen: Boolean = false,
)