package com.example.superfitcompose.domain.usecases

class ValidationUseCase {

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+.[a-zA-Z0-9]+\$"

//    private fun String.isEmailValid(): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
//    }

    private fun String.isEmailValid(): Boolean {
        return this.matches(emailRegex.toRegex())
    }

    operator fun invoke(
        email: String,
        code: String? = null,
        codeConfirmation: String? = null,
        userName: String? = null
    ): String {
        var answer = ""

        if (userName?.isEmpty() == true){
            answer += "Username is Empty!\n"
        }

        if ((code != null || codeConfirmation != null) && code != codeConfirmation) {
            answer += "Code and confirmation not equal!\n"
        }

        if (code?.isNumeric() == false || codeConfirmation?.isNumeric() == false) {
            answer += "Code can not contains any symbols except numbers\n"
        }

        if (code != null && codeConfirmation != null) {
            if (!"[1-9]{4}".toRegex().matches(code) || !"[1-9]{4}".toRegex()
                    .matches(codeConfirmation)
            ) {
                answer += "Possible length of code is 4 digits\n"
            }
        }



        if (!email.trim().isEmailValid()) {
            answer += "Email is incorrect!\n"
        }

        return answer
    }

    private fun String.isNumeric(): Boolean {
        return try {
            this.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}