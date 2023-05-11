package com.example.superfitcompose.ui.main.exercises

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.ui.auth.login.LoginPreview
import com.example.superfitcompose.ui.main.exercises.AllExercisesIntent.ClickedOnExercise
import com.example.superfitcompose.ui.main.exercises.AllExercisesIntent.NavigationProcessed

class AllExercisesViewModel : ViewModel(), IntentHandler<AllExercisesIntent> {

    private val _screenState = MutableLiveData(AllExercisesViewState())

    fun getScreenState(): LiveData<AllExercisesViewState> = _screenState


    override fun processIntent(intent: AllExercisesIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is ClickedOnExercise -> {
                _screenState.value = state.copy(navigateToExercise = intent.exercise)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateToExercise = null)
            }
        }
    }
}