package com.example.superfitcompose.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.ui.IntentHandler
import com.example.superfitcompose.R
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.domain.usecases.SaveExerciseProgressUseCase
import com.example.superfitcompose.ui.exercise.ExerciseIntent.ErrorProceed
import com.example.superfitcompose.ui.exercise.ExerciseIntent.ExerciseStepDone
import com.example.superfitcompose.ui.exercise.ExerciseIntent.FinishExercise
import com.example.superfitcompose.ui.exercise.ExerciseIntent.LoadExerciseData
import com.example.superfitcompose.ui.exercise.ExerciseIntent.PauseExercise
import com.example.superfitcompose.ui.exercise.ExerciseIntent.StartExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.properties.Delegates

class ExerciseViewModel(
    private val getTrainingHistoryUseCase: GetTrainingHistoryUseCase,
    private val saveExerciseProgressUseCase: SaveExerciseProgressUseCase
) :
    ViewModel(), IntentHandler<ExerciseIntent> {

    private val _screenState = MutableLiveData(ExerciseViewState())

    private var beginValue by Delegates.notNull<Int>()
    private lateinit var currentTrainingType: TrainingType

    @Volatile
    private var exerciseStepDone: Boolean = false

    @Volatile
    private var exerciseStep: Int = 1


    fun getScreenState(): LiveData<ExerciseViewState> = _screenState

    private var job: Job? = null

    private suspend fun plankTimer() {
        delay(timeMillis = 1000)
        exerciseStepDone = true
    }

    private fun start() {
        if (job?.isActive == true) return
        var state = _screenState.value ?: return

        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _screenState.value =
                    state.copy(pause = false, finished = false, launchedMessage = false)
            }

            while (isActive) {
                state = _screenState.value ?: return@launch
                if (state.counter <= 0) {
                    stop()
                }

                if (currentTrainingType == TrainingType.PLANK) {
                    plankTimer()
                }

                if (exerciseStepDone) {
                    exerciseStepDone = false

                    withContext(Dispatchers.Main) {
                        _screenState.value =
                            state.copy(counter = state.counter - exerciseStep)
                    }
                }
            }
        }
    }

    private fun pause() {
        job?.cancel()
        _screenState.value = _screenState.value?.copy(pause = true)
    }

    private suspend fun stop() {
        var state = _screenState.value ?: return

        if (!state.saved) {
            viewModelScope.launch {
                saveExerciseProgress(if (currentTrainingType == TrainingType.CRUNCH) beginValue else beginValue - state.counter)
            }
        } else {
            withContext(Dispatchers.Main) {
                _screenState.value = state.copy(error = R.string.try_to_save_twice_error)
                state = _screenState.value ?: return@withContext
            }
        }

        withContext(Dispatchers.Main) {
            _screenState.value = state.copy(pause = true, finished = true)
        }
        job?.cancel()
    }


    private suspend fun saveExerciseProgress(count: Int) {
        saveExerciseProgressUseCase(
            currentTrainingType,
            count,
            Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
        ).collect {
            if (it is ApiResponse.Success) {
                withContext(Dispatchers.Main) {
                    _screenState.value = _screenState.value?.copy(saved = true)
                }
            }
        }
    }

    private suspend fun loadExerciseDataSucceed(trainingType: TrainingType): Boolean {
        currentTrainingType = trainingType

        return getTrainingHistoryUseCase().last().let { trainings ->
            when (trainings) {
                is ApiResponse.Loading -> {
                    return@let false
                }

                is ApiResponse.Failure -> {
                    return@let false
                }

                is ApiResponse.Success -> {
                    beginValue = trainings.data.filter { it.exercise == trainingType }
                        .maxByOrNull { it.repeatCount }?.repeatCount?.plus(
                            getPlusDeltaForExercise(
                                trainingType
                            )
                        )
                        ?: getStartValueForExercise(trainingType)
                    return@let true
                }
            }
        }
    }

    private fun getPlusDeltaForExercise(trainingType: TrainingType): Int {
        return when (trainingType) {
            TrainingType.RUNNING -> 100
            else -> 5
        }
    }

    private fun getStartValueForExercise(trainingType: TrainingType): Int {
        return when (trainingType) {
            TrainingType.PLANK -> 20
            TrainingType.RUNNING -> 1000
            TrainingType.CRUNCH -> 10
            TrainingType.PUSH_UP -> 10
            TrainingType.SQUATS -> 10
        }
    }

    override fun processIntent(intent: ExerciseIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is LoadExerciseData -> {
                viewModelScope.launch {
                    if (loadExerciseDataSucceed(intent.trainingType)) {
                        withContext(Dispatchers.Main) {
                            _screenState.value =
                                state.copy(counter = beginValue, beginCounterValue = beginValue)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            _screenState.value =
                                state.copy(error = R.string.fail_load_data)
                        }
                    }
                }

            }

            is ExerciseStepDone -> {
                if (state.beginCounterValue != 0 && !state.pause) {
                    exerciseStep = intent.count
                    exerciseStepDone = true
                }
            }

            is StartExercise -> {
                start()
            }

            is PauseExercise -> {
                pause()
            }

            is FinishExercise -> {
                viewModelScope.launch {
                    stop()
                }
            }

            is ErrorProceed -> {
                _screenState.value = state.copy(error = null)
            }
        }
    }
}