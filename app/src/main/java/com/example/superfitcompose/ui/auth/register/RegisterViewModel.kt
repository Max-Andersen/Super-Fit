package com.example.superfitcompose.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.toDomain
import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.SimpleMessage
import com.example.superfitcompose.domain.usecases.GetTokensUseCase
import com.example.superfitcompose.domain.usecases.RegisterUseCase
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import com.example.superfitcompose.domain.usecases.ValidationUseCase
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.CodeConfirmationInput
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.CodeInput
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.EmailInput
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.ErrorProcessed
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.NavigationProcessed
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.SignInNavigationButtonClicked
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.SignUpButtonClicked
import com.example.superfitcompose.ui.auth.register.RegisterScreenIntent.UserNameInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel(), IntentHandler<RegisterScreenIntent> {

    private val _screenState = MutableLiveData<RegisterViewState>()

    fun getScreenState(): LiveData<RegisterViewState> = _screenState

    init {
        _screenState.postValue(RegisterViewState())
    }

    override fun processIntent(intent: RegisterScreenIntent) {
        when (intent) {
            is ErrorProcessed -> {
                _screenState.value = _screenState.value?.copy(errorMessage = "")
            }

            is SignInNavigationButtonClicked -> {
                _screenState.value = _screenState.value?.copy(navigateToLogin = true)
            }

            is NavigationProcessed -> {
                _screenState.value =
                    _screenState.value?.copy(navigateToLogin = false, navigateMainScreen = false)
            }

            is SignUpButtonClicked -> {
                val email = _screenState.value!!.email
                val code = _screenState.value!!.code
                val codeConfirmation = _screenState.value!!.codeConfirmation

                val validationAnswer = ValidationUseCase(email, code, codeConfirmation)()

                if (validationAnswer.isEmpty()) {
                    viewModelScope.launch {
                        val registerAnswer: ApiResponse<SimpleMessage> =
                            withContext(Dispatchers.IO) {
                                registerRequest(email, code)
                            }

                        if (registerAnswer is ApiResponse.Success) {
                            GetTokensUseCase(email, code)().let { tokens ->
                                when (tokens) {
                                    is ApiResponse.Success -> {
                                        SharedPreferencesInteractor().updateAccessToken(tokens.data.access)
                                        SharedPreferencesInteractor().updateRefreshToken(tokens.data.refresh)
                                        Log.d("!", "success")
                                        withContext(Dispatchers.Main) {
                                            _screenState.value = _screenState.value?.copy(navigateMainScreen = true)
                                        }
                                    }

                                    is ApiResponse.Failure -> {
                                        withContext(Dispatchers.Main) {
                                            _screenState.value = _screenState.value?.copy(errorMessage = tokens.errorMessage)
                                        }
                                    }

                                    is ApiResponse.Loading -> {
                                        Log.d("!", "load")
                                    }
                                }
                            }
                        }


                    }
                } else {
                    _screenState.value = _screenState.value?.copy(errorMessage = validationAnswer)
                }
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
                _screenState.value =
                    _screenState.value?.copy(codeConfirmation = intent.codeConfirmation)
            }
        }
    }

    private suspend fun registerRequest(email: String, code: String): ApiResponse<SimpleMessage> {
        return RegisterUseCase(AuthCredential(email, code))().last().let {
            when (it) {
                is ApiResponse.Loading -> {
                    return@let ApiResponse.Loading
                }

                is ApiResponse.Success -> {
                    return@let ApiResponse.Success(it.data.toDomain())
                }

                is ApiResponse.Failure -> {
                    withContext(Dispatchers.Main) {
                        _screenState.value =
                            _screenState.value?.copy(errorMessage = it.errorMessage)
                    }
                    return@let ApiResponse.Failure(it.errorMessage, it.code)

                }
            }
        }
    }

}