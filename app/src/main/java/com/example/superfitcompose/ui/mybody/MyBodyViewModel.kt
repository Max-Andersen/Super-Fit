package com.example.superfitcompose.ui.mybody

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.usecases.GetBodyParamsUseCase
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnAddImage
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnSeeAllProgress
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnStatistics
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnTrainProgress
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnUpdateHeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnUpdateWeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.EnterHeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.EnterWeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.GetBodyParams
import com.example.superfitcompose.ui.mybody.MyBodyIntent.GetLastImages
import com.example.superfitcompose.ui.mybody.MyBodyIntent.NavigationProcessed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyBodyViewModel(
    private val getBodyParamsUseCase: GetBodyParamsUseCase,
) : ViewModel(), IntentHandler<MyBodyIntent> {

    private val _screenState = MutableLiveData(MyBodyViewState())


    fun getViewState(): LiveData<MyBodyViewState> = _screenState

    private var height = 0
    private var weight = 0


    override fun processIntent(intent: MyBodyIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is GetBodyParams -> {
                viewModelScope.launch {
                    getBodyParamsUseCase().collect {
                        (it as? ApiResponse.Success)?.let {
                            withContext(Dispatchers.Main) {
                                val lastData = it.data.lastOrNull()

                                height = lastData?.height ?: 0
                                weight = lastData?.weight ?: 0

                                _screenState.value =
                                    state.copy(
                                        weight = weight,
                                        height = height,
                                        editWeight = true,
                                        editHeight = true
                                    )
                            }
                        }
                    }
                }
            }

            is GetLastImages -> {}

            is ClickedOnUpdateWeight -> {}

            is ClickedOnUpdateHeight -> {}

            is EnterHeight -> {
                _screenState.value = state.copy(inputHeight = intent.height)
            }

            is EnterWeight -> {
                _screenState.value = state.copy(inputWeight = intent.weight)

            }

            is ClickedOnSeeAllProgress -> {}

            is ClickedOnAddImage -> {}

            is ClickedOnTrainProgress -> {}

            is ClickedOnStatistics -> {}

            is NavigationProcessed -> {}
        }
    }

}