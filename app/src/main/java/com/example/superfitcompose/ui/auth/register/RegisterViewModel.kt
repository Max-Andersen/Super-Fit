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

class RegisterViewModel(
    private val validationUseCase: ValidationUseCase,
    private val getTokensUseCase: GetTokensUseCase,
    private val registerUseCase: RegisterUseCase,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel(), IntentHandler<RegisterScreenIntent> {

    private val _screenState = MutableLiveData<RegisterViewState>()

    fun getScreenState(): LiveData<RegisterViewState> = _screenState

    init {
        _screenState.postValue(RegisterViewState())
    }

    override fun processIntent(intent: RegisterScreenIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is ErrorProcessed -> {
                _screenState.value = state.copy(errorMessage = "")
            }

            is SignInNavigationButtonClicked -> {
                _screenState.value = state.copy(navigateToLogin = true)
            }

            is NavigationProcessed -> {
                _screenState.value =
                    state.copy(navigateToLogin = false, navigateMainScreen = false)
            }

            is SignUpButtonClicked -> {

                val email = state.email.trim()
                val code = state.code.trim()
                val codeConfirmation = state.codeConfirmation.trim()
                val userName = state.username.trim()

                val validationAnswer = validationUseCase(email, code, codeConfirmation, userName)

                if (validationAnswer.isEmpty()) {
                    viewModelScope.launch {
                        val registerAnswer: ApiResponse<SimpleMessage> =
                            registerRequest(email, code)

                        if (registerAnswer is ApiResponse.Success) {
                            getTokensUseCase(email, code).let { tokens ->
                                when (tokens) {
                                    is ApiResponse.Success -> {
                                        sharedPreferencesInteractor.updateAccessToken(tokens.data.access)
                                        sharedPreferencesInteractor.updateRefreshToken(tokens.data.refresh)
                                        sharedPreferencesInteractor.updateUserLogin(email)
                                        sharedPreferencesInteractor.updateUserPassword(code)

                                        withContext(Dispatchers.Main) {
                                            _screenState.value =
                                                state.copy(navigateMainScreen = true)
                                        }
                                    }

                                    is ApiResponse.Failure -> {
                                        withContext(Dispatchers.Main) {
                                            _screenState.value =
                                                state.copy(errorMessage = tokens.errorMessage)
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
                    _screenState.value = state.copy(errorMessage = validationAnswer.dropLast(2))
                }
            }

            is UserNameInput -> {
                _screenState.value = state.copy(username = intent.userName)
            }

            is EmailInput -> {
                _screenState.value = state.copy(email = intent.email)
            }

            is CodeInput -> {
                _screenState.value = state.copy(code = intent.code)
            }

            is CodeConfirmationInput -> {
                _screenState.value =
                    state.copy(codeConfirmation = intent.codeConfirmation)
            }
        }
    }

    private suspend fun registerRequest(email: String, code: String): ApiResponse<SimpleMessage> {
        return registerUseCase(AuthCredential(email, code)).last().let {
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