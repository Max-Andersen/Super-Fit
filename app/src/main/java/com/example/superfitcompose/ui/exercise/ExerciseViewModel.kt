package com.example.superfitcompose.ui.exercise

//class ExerciseViewModel: ViewModel(), IntentHandler<ExerciseIntent> {
//    private val _screenState = MutableLiveData(ExerciseViewState())
//
//    fun getScreenState(): LiveData<ExerciseViewState> = _screenState
//
//
//    override fun processIntent(intent: ExerciseIntent) {
//        val state = _screenState ?: return
//
//        when(intent){
//
//            else -> {}
//        }
//
//    }
//
//}

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerViewModel : ViewModel() {

    private val _screenState = MutableLiveData(ExerciseViewState())

    fun getScreenState(): LiveData<ExerciseViewState> = _screenState

    private var job: Job? = null

    private suspend fun runningTimer(){
        delay(timeMillis = 1000)
    }

    fun start(initValue: Int = 10) {
        if (job?.isActive == true) return

        var state = _screenState.value ?: return

        if (state.counter == 0) _screenState.value =
            state.copy(counter = initValue, beginCounterValue = initValue)


        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                _screenState.value = _screenState.value?.copy(pause = false, finished = false)
            }


            while (isActive) {
                state = _screenState.value ?: return@launch
                if (state.counter <= 0) {
                    withContext(Dispatchers.Main) {
                        _screenState.value = state.copy(pause = true, finished = true)
                    }
                    job?.cancel()
                    return@launch
                }


                //check exercise and select right count function

                runningTimer()

                withContext(Dispatchers.Main) {
                    _screenState.value =
                        state.copy(counter = state.counter - 1, beginCounterValue = initValue)
                }

            }
        }
    }

    fun pause() {
        job?.cancel()
        _screenState.value = _screenState.value?.copy(pause = true)
    }

    fun stop() {
        val state = _screenState.value ?: return
        job?.cancel()
        _screenState.value = state.copy(pause = true, finished = true)
    }
}