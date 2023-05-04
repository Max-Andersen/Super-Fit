package com.example.superfitcompose.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.*
class RegisterViewModel: ViewModel(), IntentHandler<RegisterScreenIntent> {

    private val _screenState = MutableLiveData<RegisterViewState>()

    fun getScreenState(): LiveData<RegisterViewState> = _screenState

    init {
        _screenState.postValue(RegisterViewState())
    }


    override fun processIntent(intent: RegisterScreenIntent) {
        when(intent){
            is ErrorProcessed -> {
                _screenState.value = _screenState.value?.copy(errorMessage = "")
            }

            is SignInNavigationButtonClicked -> {
                _screenState.value = _screenState.value?.copy(navigateToLogin = true)
            }

            is NavigationProcessed -> {
                _screenState.value = _screenState.value?.copy(navigateToLogin = false, navigateMainScreen = false)
            }

            is SignUpButtonClicked -> {
                // Todo validation + request
            }

            is UserNameInput -> {
                _screenState.value = _screenState.value?.copy(username = intent.userName)
            }

            is EmailInput -> {
                _screenState.value = _screenState.value?.copy(email = intent.email)
            }

            is CodeInput -> {
                _screenState.value = _screenState.value?.copy(code = intent.code)
            }

            is CodeConfirmationInput -> {
                _screenState.value = _screenState.value?.copy(codeConfirmation = intent.codeConfirmation)
            }
        }
    }

}