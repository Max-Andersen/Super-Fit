package com.example.superfitcompose.ui.auth

sealed class AuthIntent {
    data class UserNameInput(val userName: String): AuthIntent()
    object EnterCodeButtonClicked: AuthIntent()
    object SignUpNavigationButtonClicked : AuthIntent()


    data class CodeNumberInput(val number: String): AuthIntent()


    data class RegisterUserNameInput(val userName: String): AuthIntent()
    data class RegisterEmailInput(val email: String): AuthIntent()
    data class RegisterCodeInput(val code: String): AuthIntent()
    data class RegisterCodeConfirmationInput(val codeConfirmation: String): AuthIntent()

    object SignUpButtonClicked: AuthIntent()
    object SignInNavigationButtonClicked : AuthIntent()

}
