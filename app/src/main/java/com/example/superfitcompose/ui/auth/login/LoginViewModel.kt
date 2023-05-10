package com.example.superfitcompose.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.domain.usecases.ValidationUseCase
import com.example.superfitcompose.ui.auth.login.LoginScreenIntent.*


class LoginViewModel : ViewModel(), IntentHandler<LoginScreenIntent> {
    private val _screenState = MutableLiveData<LoginViewState>()

    fun getScreenState(): LiveData<LoginViewState> = _screenState

    init {
        _screenState.value = LoginViewState()
    }

    private var password = ""



    override fun processIntent(intent: LoginScreenIntent) {
        when (intent) {
            is UserNameInput -> {
                _screenState.value = _screenState.value!!.copy(login = intent.userName)
            }

            is EnterCodeButtonClicked -> {
                val validationAnswer = ValidationUseCase(_screenState.value!!.login)()
                Log.d("!", validationAnswer)
                Log.d("!", validationAnswer.isEmpty().toString())
                if (validationAnswer.isEmpty()){
                    _screenState.value = _screenState.value!!.copy(navigateToEnterPassword = true, navigateToRegister = false)
                } else{
                    _screenState.value = _screenState.value!!.copy(errorMessage = validationAnswer)
                }
            }

            is SignUpNavigationButtonClicked -> {
                _screenState.value = _screenState.value!!.copy(navigateToEnterPassword = false, navigateToRegister = true)
            }

            is ErrorProcessed -> {
                _screenState.value = _screenState.value!!.copy(errorMessage = "")
            }

            is NavigationProcessed -> {
                _screenState.value = _screenState.value!!.copy(navigateToEnterPassword = false, navigateToRegister = false)
            }
        }
    }


}