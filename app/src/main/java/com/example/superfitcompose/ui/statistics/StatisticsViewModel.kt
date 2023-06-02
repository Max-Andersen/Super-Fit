package com.example.superfitcompose.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.usecases.GetBodyParamsUseCase
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.ui.statistics.StatisticsIntent.ClickedOnNavigateBack
import com.example.superfitcompose.ui.statistics.StatisticsIntent.LoadData
import com.example.superfitcompose.ui.statistics.StatisticsIntent.NavigationProcessed
import com.example.superfitcompose.ui.statistics.StatisticsIntent.SelectCrunchHistory
import com.example.superfitcompose.ui.statistics.StatisticsIntent.SelectPlankHistory
import com.example.superfitcompose.ui.statistics.StatisticsIntent.SelectPushUpsHistory
import com.example.superfitcompose.ui.statistics.StatisticsIntent.SelectRunningHistory
import com.example.superfitcompose.ui.statistics.StatisticsIntent.SelectSquatsHistory
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getTrainingHistoryUseCase: GetTrainingHistoryUseCase,
    private val getBodyParamsUseCase: GetBodyParamsUseCase
) : ViewModel(), IntentHandler<StatisticsIntent> {
    private val _screenState = MutableLiveData(StatisticsViewState())

    fun getViewState(): LiveData<StatisticsViewState> = _screenState

    override fun processIntent(intent: StatisticsIntent) {
        var state = _screenState.value ?: return
        when (intent) {
            is SelectPushUpsHistory -> {
                _screenState.value = state.copy(selectedExercise = TrainingType.PUSH_UP)
            }

            is SelectPlankHistory -> {
                _screenState.value = state.copy(selectedExercise = TrainingType.PLANK)
            }

            is SelectCrunchHistory -> {
                _screenState.value = state.copy(selectedExercise = TrainingType.CRUNCH)
            }

            is SelectSquatsHistory -> {
                _screenState.value = state.copy(selectedExercise = TrainingType.SQUATS)
            }

            is SelectRunningHistory -> {
                _screenState.value = state.copy(selectedExercise = TrainingType.RUNNING)
            }

            is LoadData -> {
                viewModelScope.launch {
                    getTrainingHistoryUseCase().collect {
                        if (it is ApiResponse.Success) {
                            val sortedAndUnique =
                                it.data.sortedBy { exercise -> exercise.date }.distinct()
                            _screenState.value = state.copy(
                                pushUpsHistory = sortedAndUnique.filter { exercise -> exercise.exercise == TrainingType.PUSH_UP },
                                plankHistory = sortedAndUnique.filter { exercise -> exercise.exercise == TrainingType.PLANK },
                                crunchHistory = sortedAndUnique.filter { exercise -> exercise.exercise == TrainingType.CRUNCH },
                                squatsHistory = sortedAndUnique.filter { exercise -> exercise.exercise == TrainingType.SQUATS },
                                runningHistory = sortedAndUnique.filter { exercise -> exercise.exercise == TrainingType.RUNNING }
                            )
                            state = _screenState.value ?: return@collect
                        }
                    }

                    getBodyParamsUseCase().collect {
                        if (it is ApiResponse.Success) {
                            _screenState.value =
                                state.copy(weightHistory = it.data.sortedBy { item -> item.date }
                                    .distinct())
                        }
                    }
                }
            }

            is ClickedOnNavigateBack -> {
                _screenState.value = state.copy(navigateBack = true)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateBack = false)
            }
        }
    }
}