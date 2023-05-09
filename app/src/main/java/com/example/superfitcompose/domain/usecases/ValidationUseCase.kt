package com.example.superfitcompose.domain.usecases

class ValidationUseCase(private val email: String, private val code: String? = null, private val codeConfirmation: String? = null) {
    private fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    operator fun invoke(): String {
        var answer = ""

        if ((code != null || codeConfirmation != null) && code != codeConfirmation){
            answer += "Code and confirmation not equal!"
        }

        if (!email.isEmailValid()){
            answer += "Email is incorrect!"
        }

        return answer
    }
}