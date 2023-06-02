package com.example.superfitcompose.ui.auth.code

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.usecases.GetTokensUseCase
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import com.example.superfitcompose.ui.auth.code.CodeInputScreenIntent.CodeNumberInput
import com.example.superfitcompose.ui.auth.code.CodeInputScreenIntent.ErrorProcessed
import com.example.superfitcompose.ui.auth.code.CodeInputScreenIntent.NavigationProcessed
import com.example.superfitcompose.ui.auth.code.CodeInputScreenIntent.SetEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CodeInputViewModel(
    private val getTokensUseCase: GetTokensUseCase,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel(), IntentHandler<CodeInputScreenIntent> {

    private val _screenState = MutableLiveData<CodeEnterViewState>(CodeEnterViewState())

    fun getScreenState(): LiveData<CodeEnterViewState> = _screenState

    private var password = ""

    override fun processIntent(intent: CodeInputScreenIntent) {
        when (intent) {
            is SetEmail -> {
                _screenState.value = _screenState.value?.copy(email = intent.email)
            }

            is CodeNumberInput -> {
                password += intent.number
                if (password.length == 4) {
                    viewModelScope.launch {
                        val email = _screenState.value!!.email

                        getTokensUseCase(email, password).let { tokens ->
                            when (tokens) {
                                is ApiResponse.Success -> {
                                    sharedPreferencesInteractor.updateAccessToken(tokens.data.access)
                                    sharedPreferencesInteractor.updateRefreshToken(tokens.data.refresh)
                                    sharedPreferencesInteractor.updateUserLogin(email)
                                    sharedPreferencesInteractor.updateUserPassword(password)
                                    Log.d("!", "success")
                                    withContext(Dispatchers.Main) {
                                        _screenState.value =
                                            _screenState.value?.copy(navigateToMainScreen = true)
                                    }
                                }

                                is ApiResponse.Failure -> {
                                    password = ""
                                    withContext(Dispatchers.Main) {
                                        _screenState.value =
                                            _screenState.value?.copy(errorMessage = tokens.errorMessage)
                                    }
                                }

                                is ApiResponse.Loading -> {
                                    Log.d("!", "load")
                                }
                            }
                        }

                    }
                }

            }

            is ErrorProcessed -> {
                _screenState.value = _screenState.value?.copy(errorMessage = "")
            }

            is NavigationProcessed -> {
                _screenState.value = _screenState.value?.copy(navigateToMainScreen = false)
            }
        }
    }
}