package com.example.superfitcompose.domain.usecases

import android.util.Log

class ValidationUseCase(
    private val email: String,
    private val code: String? = null,
    private val codeConfirmation: String? = null
) {
    private fun String.isEmailValid(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    operator fun invoke(): String {
        var answer = ""

        if ((code != null || codeConfirmation != null) && code != codeConfirmation) {
            answer += "Code and confirmation not equal!\n"
        }

        if (code?.isNumeric() == false || codeConfirmation?.isNumeric() == false) {
            answer += "Code can not contains any symbols except numbers\n"
        }

        Log.d("!!!!!", code.toString())
        Log.d("!!!!!", codeConfirmation.toString())

        if (code != null && codeConfirmation != null){
            if (!"\\d{4}".toRegex().matches(code) || !"\\d{4}".toRegex().matches(codeConfirmation)) {
                answer += "Possible length of code is 4 digits\n"
            }
        }



        if (!email.isEmailValid()) {
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