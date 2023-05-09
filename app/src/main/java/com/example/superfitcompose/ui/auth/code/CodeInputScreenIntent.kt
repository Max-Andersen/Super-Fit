package com.example.superfitcompose.ui.auth.code

import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent

sealed class CodeInputScreenIntent {

    data class SetEmail(val email: String): CodeInputScreenIntent()
    data class CodeNumberInput(val number: String) : CodeInputScreenIntent()
    object ErrorProcessed: CodeInputScreenIntent()
    object NavigationProcessed : CodeInputScreenIntent()

}
