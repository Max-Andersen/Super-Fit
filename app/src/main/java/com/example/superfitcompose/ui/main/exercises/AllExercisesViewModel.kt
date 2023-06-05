package com.example.superfitcompose.ui.main.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.ui.IntentHandler
import com.example.superfitcompose.ui.main.exercises.AllExercisesIntent.ClickedOnExercise
import com.example.superfitcompose.ui.main.exercises.AllExercisesIntent.NavigationProcessed

class AllExercisesViewModel : ViewModel(), IntentHandler<AllExercisesIntent> {

    private val _screenState = MutableLiveData(AllExercisesViewState())

    fun getScreenState(): LiveData<AllExercisesViewState> = _screenState


    override fun processIntent(intent: AllExercisesIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is ClickedOnExercise -> {
                _screenState.value = state.copy(navigateToTrainingType = intent.trainingType)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateToTrainingType = null)
            }
        }
    }
}