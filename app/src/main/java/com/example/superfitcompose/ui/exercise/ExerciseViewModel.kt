package com.example.superfitcompose.ui.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.domain.usecases.SaveExerciseProgressUseCase
import com.example.superfitcompose.ui.exercise.ExerciseIntent.ExerciseStepDone
import com.example.superfitcompose.ui.exercise.ExerciseIntent.FinishExercise
import com.example.superfitcompose.ui.exercise.ExerciseIntent.LoadExerciseData
import com.example.superfitcompose.ui.exercise.ExerciseIntent.PauseExercise
import com.example.superfitcompose.ui.exercise.ExerciseIntent.StartExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.properties.Delegates

class ExerciseViewModel(
    private val getTrainingHistoryUseCase: GetTrainingHistoryUseCase = GetTrainingHistoryUseCase(),
    private val saveExerciseProgressUseCase: SaveExerciseProgressUseCase = SaveExerciseProgressUseCase()
) :
    ViewModel(), IntentHandler<ExerciseIntent> {

    private val _screenState = MutableLiveData(ExerciseViewState())

    private var beginValue by Delegates.notNull<Int>()
    private lateinit var trainingType: TrainingType

    @Volatile
    private var exerciseStepDone: Boolean = false

    fun getScreenState(): LiveData<ExerciseViewState> = _screenState

    private var job: Job? = null

    private suspend fun plankTimer() {
        delay(timeMillis = 1000)
        exerciseStepDone = true
    }

    private fun start() {
        if (job?.isActive == true) return

        var state = _screenState.value ?: return

        if (state.counter == 0) _screenState.value =
            state.copy(counter = beginValue)


        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _screenState.value = _screenState.value?.copy(pause = false, finished = false)
            }


            while (isActive) {
                state = _screenState.value ?: return@launch
                if (state.counter <= 0) {
                    saveExerciseProgress(beginValue - state.counter)

                    withContext(Dispatchers.Main) {
                        _screenState.value = state.copy(pause = true, finished = true)
                    }
                    job?.cancel()
                    return@launch
                }


                if (trainingType == TrainingType.PLANK) {
                    plankTimer()
                }

                if (exerciseStepDone) {
                    exerciseStepDone = false

                    withContext(Dispatchers.Main) {
                        _screenState.value =
                            state.copy(counter = state.counter - 1)
                    }
                }
            }
        }
    }

    private fun pause() {
        job?.cancel()
        _screenState.value = _screenState.value?.copy(pause = true)
    }

    private fun stop() {
        val state = _screenState.value ?: return
        job?.cancel()
        _screenState.value = state.copy(pause = true, finished = true)
        viewModelScope.launch {
            saveExerciseProgress(if (trainingType == TrainingType.CRUNCH) beginValue else beginValue - state.counter)
        }
    }


    private suspend fun saveExerciseProgress(count: Int) {
        saveExerciseProgressUseCase(
            trainingType,
            count,
            Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
        ).collect()
    }

    private suspend fun loadExerciseDataSucceed(trainingType: TrainingType): Boolean {
        this@ExerciseViewModel.trainingType = trainingType

        return getTrainingHistoryUseCase().last().let { trainings ->
            when (trainings) {
                is ApiResponse.Loading -> {
                    return@let false
                }

                is ApiResponse.Failure -> {
                    return@let false
                }

                is ApiResponse.Success -> {
                    val startExerciseValue = when (trainingType) {
                        TrainingType.PLANK -> 20
                        TrainingType.RUNNING -> 1000
                        TrainingType.CRUNCH -> 10
                        TrainingType.PUSH_UP -> 10
                        TrainingType.SQUATS -> 10
                    }

                    beginValue = trainings.data.filter { it.exercise == trainingType }
                        .maxByOrNull { it.repeatCount }?.repeatCount?.plus(5) ?: startExerciseValue
                    return@let true
                }
            }
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
                        // Error
                    }
                }

            }

            is ExerciseStepDone -> {
                if (state.beginCounterValue != 0) {
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
                stop()
            }
        }
    }
}