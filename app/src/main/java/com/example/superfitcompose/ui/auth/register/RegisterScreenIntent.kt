package com.example.superfitcompose.ui.auth.register

import com.example.superfitcompose.ui.auth.login.LoginScreenIntent


sealed class RegisterScreenIntent {

    data class UserNameInput(val userName: String): RegisterScreenIntent()
    data class EmailInput(val email: String): RegisterScreenIntent()
    data class CodeInput(val code: String): RegisterScreenIntent()
    data class CodeConfirmationInput(val codeConfirmation: String): RegisterScreenIntent()

    object SignUpButtonClicked: RegisterScreenIntent()

    object SignInNavigationButtonClicked : RegisterScreenIntent()
    object NavigationProcessed : RegisterScreenIntent()

    object ErrorProcessed: RegisterScreenIntent()

}