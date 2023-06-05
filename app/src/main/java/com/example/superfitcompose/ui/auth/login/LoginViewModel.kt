package com.example.superfitcompose.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.ui.IntentHandler
import com.example.superfitcompose.domain.usecases.ValidationUseCase
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.EnterCodeButtonClicked
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.ErrorProcessed
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.NavigationProcessed
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.SignUpNavigationButtonClicked
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.UserNameInput


class LoginViewModel(private val validationUseCase: ValidationUseCase) : ViewModel(),
    IntentHandler<LoginScreenIntent> {
    private val _screenState = MutableLiveData<LoginViewState>()

    fun getScreenState(): LiveData<LoginViewState> = _screenState

    init {
        _screenState.value = LoginViewState()
    }

    override fun processIntent(intent: LoginScreenIntent) {
        val state = _screenState.value ?: return
        when (intent) {
            is UserNameInput -> {
                _screenState.value = state.copy(login = intent.userName)
            }

            is EnterCodeButtonClicked -> {
                val validationAnswer = validationUseCase(email = state.login.trim())
                if (validationAnswer.isEmpty()) {
                    _screenState.value =
                        state.copy(navigateToEnterPassword = true, navigateToRegister = false)
                } else {
                    _screenState.value = state.copy(errorMessage = validationAnswer.dropLast(2))
                }
            }

            is SignUpNavigationButtonClicked -> {
                _screenState.value =
                    state.copy(navigateToEnterPassword = false, navigateToRegister = true)
            }

            is ErrorProcessed -> {
                _screenState.value = state.copy(errorMessage = "")
            }

            is NavigationProcessed -> {
                _screenState.value =
                    state.copy(navigateToEnterPassword = false, navigateToRegister = false)
            }
        }
    }


}