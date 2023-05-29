package com.example.superfitcompose.ui.trainprogress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.domain.usecases.DownloadPhotoUseCase
import com.example.superfitcompose.ui.statistics.StatisticsIntent
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent.ClickedOnNavigateBack
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent.NavigationProcessed

class TrainProgressViewModel(
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
) : ViewModel(), IntentHandler<TrainProgressIntent> {
    private val _screenState = MutableLiveData(TrainProgressViewState())

    fun getViewState(): LiveData<TrainProgressViewState> = _screenState

    override fun processIntent(intent: TrainProgressIntent) {
        val state = _screenState.value ?: return
        when(intent){
            is ClickedOnNavigateBack -> {
                _screenState.value = state.copy(navigateBack = true)
            }
            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateBack = false)
            }
        }
    }

}