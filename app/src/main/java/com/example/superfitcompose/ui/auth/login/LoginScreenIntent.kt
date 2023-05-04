package com.example.superfitcompose.ui.auth.login

import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent

sealed class LoginScreenIntent {
    object ErrorProcessed: LoginScreenIntent()

    data class UserNameInput(val userName: String): LoginScreenIntent()
    object EnterCodeButtonClicked: LoginScreenIntent()
    object SignUpNavigationButtonClicked : LoginScreenIntent()
    object NavigationProcessed : LoginScreenIntent()





}
