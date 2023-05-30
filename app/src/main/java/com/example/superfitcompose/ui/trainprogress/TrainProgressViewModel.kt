package com.example.superfitcompose.ui.trainprogress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.usecases.GetTrainProgressForExercise
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent.ClickedOnNavigateBack
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent.LoadData
import com.example.superfitcompose.ui.trainprogress.TrainProgressIntent.NavigationProcessed
import kotlinx.coroutines.launch

class TrainProgressViewModel(
    private val getTrainingHistoryUseCase: GetTrainingHistoryUseCase,
    private val getTrainProgressForExercise: GetTrainProgressForExercise
) : ViewModel(), IntentHandler<TrainProgressIntent> {
    private val _screenState = MutableLiveData(TrainProgressViewState())

    fun getViewState(): LiveData<TrainProgressViewState> = _screenState

    override fun processIntent(intent: TrainProgressIntent) {
        val state = _screenState.value ?: return
        when (intent) {
            is LoadData -> {
                viewModelScope.launch {
                    getTrainingHistoryUseCase().collect {
                        if (it is ApiResponse.Success) {
                            val sorted = it.data.sortedBy { training -> training.date }
                            val pushUps =
                                sorted.filter { training -> training.exercise == TrainingType.PUSH_UP }
                            val plank =
                                sorted.filter { training -> training.exercise == TrainingType.PLANK }
                            val crunch =
                                sorted.filter { training -> training.exercise == TrainingType.CRUNCH }
                            val squats =
                                sorted.filter { training -> training.exercise == TrainingType.SQUATS }
                            val running =
                                sorted.filter { training -> training.exercise == TrainingType.RUNNING }

                            _screenState.value = state.copy(
                                pushUpsTrainProgress = getTrainProgressForExercise(pushUps),
                                plankTrainProgress = getTrainProgressForExercise(plank),
                                crunchTrainProgress = getTrainProgressForExercise(crunch),
                                squatsTrainProgress = getTrainProgressForExercise(squats),
                                runningTrainProgress = getTrainProgressForExercise(running),
                            )


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