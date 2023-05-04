package com.example.superfitcompose.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.ui.auth.AuthIntent.CodeNumberInput
import com.example.superfitcompose.ui.auth.AuthIntent.EnterCodeButtonClicked
import com.example.superfitcompose.ui.auth.AuthIntent.RegisterCodeConfirmationInput
import com.example.superfitcompose.ui.auth.AuthIntent.RegisterCodeInput
import com.example.superfitcompose.ui.auth.AuthIntent.RegisterEmailInput
import com.example.superfitcompose.ui.auth.AuthIntent.RegisterUserNameInput
import com.example.superfitcompose.ui.auth.AuthIntent.SignInNavigationButtonClicked
import com.example.superfitcompose.ui.auth.AuthIntent.SignUpButtonClicked
import com.example.superfitcompose.ui.auth.AuthIntent.SignUpNavigationButtonClicked
import com.example.superfitcompose.ui.auth.AuthIntent.UserNameInput


class LoginViewModel : ViewModel(), IntentHandler<AuthIntent> {
    private val _authScreenState = MutableLiveData<AuthViewState>()

    private var password = ""

    fun getScreenState(): LiveData<AuthViewState> = _authScreenState

    init {
        _authScreenState.postValue(AuthViewState())
    }


    override fun processIntent(intent: AuthIntent) {

        when (intent) {
            is UserNameInput -> {
                _authScreenState.value = _authScreenState.value!!.copy(login = intent.userName)
            }

            is EnterCodeButtonClicked -> {
                if (ValidationUseCase(_authScreenState.value!!.login)()){
                    _authScreenState.value = _authScreenState.value!!.copy(enterUserName = false, enterPassword = true, register = false)
                } else{
                    _authScreenState.value = _authScreenState.value!!.copy(isError = true, errorMessage = "Invalid Email")
                }
            }

            is SignUpNavigationButtonClicked -> {
                _authScreenState.value = _authScreenState.value!!.copy(enterUserName = false, enterPassword = false, register = true)
            }

            is CodeNumberInput -> {
                password += intent.number
                if (password.length == 4){
                    // Todo server request
                }
            }

            is RegisterUserNameInput -> {
                _authScreenState.value = _authScreenState.value!!.copy(username = intent.userName)
            }

            is RegisterEmailInput -> {
                _authScreenState.value = _authScreenState.value!!.copy(email = intent.email)
            }

            is RegisterCodeInput -> {
                _authScreenState.value = _authScreenState.value!!.copy(code = intent.code)
            }

            is RegisterCodeConfirmationInput -> {
                _authScreenState.value = _authScreenState.value!!.copy(repeatCode = intent.codeConfirmation)
            }

            is SignUpButtonClicked -> {

            }

            is SignInNavigationButtonClicked -> {
                _authScreenState.value = _authScreenState.value!!.copy(enterUserName = true, enterPassword = false, register = false)
            }
        }
    }


}