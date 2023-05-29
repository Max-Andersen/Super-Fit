package com.example.superfitcompose.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.domain.usecases.DownloadPhotoUseCase
import com.example.superfitcompose.ui.statistics.StatisticsIntent.ClickedOnNavigateBack
import com.example.superfitcompose.ui.statistics.StatisticsIntent.NavigationProcessed

class StatisticsViewModel(
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
) : ViewModel(), IntentHandler<StatisticsIntent> {
    private val _screenState = MutableLiveData(StatisticsViewState())

    fun getViewState(): LiveData<StatisticsViewState> = _screenState

    override fun processIntent(intent: StatisticsIntent) {
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