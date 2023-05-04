package com.example.superfitcompose.ui.auth

class ValidationUseCase(private val email: String) {
    private fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    operator fun invoke(): Boolean{
        return email.isEmailValid()
    }
}