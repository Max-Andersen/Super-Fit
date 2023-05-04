package com.example.superfitcompose.ui.auth.code

data class CodeEnterViewState (
    val code: String = "",
    val isSuccess: Boolean = false,
    val errorMessage: String = "",

    val navigateToMainScreen: Boolean = false
)