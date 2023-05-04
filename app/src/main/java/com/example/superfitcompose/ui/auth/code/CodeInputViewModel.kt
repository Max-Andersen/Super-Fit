package com.example.superfitcompose.ui.auth.code

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.ui.auth.code.CodeInputScreenIntent.*

class CodeInputViewModel: ViewModel(), IntentHandler<CodeInputScreenIntent> {

    private val _screenState = MutableLiveData<CodeEnterViewState>()

    fun getScreenState(): LiveData<CodeEnterViewState> = _screenState

    init {
        _screenState.postValue(CodeEnterViewState())
    }

    private var password = ""

    override fun processIntent(intent: CodeInputScreenIntent) {
        when(intent){
            is CodeNumberInput -> {
               password += intent.number
               if (password.length == 4){
                   // Todo request
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