package com.example.superfitcompose.ui.main.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnExercise
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnMyBodyCard
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnSeeAllExercises
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnSignOut
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.NavigationProcessed

class MainScreenViewModel : ViewModel(), IntentHandler<MainScreenIntent> {
    private val _screenState = MutableLiveData(MainScreenViewState())

    fun getViewState(): LiveData<MainScreenViewState> = _screenState

    override fun processIntent(intent: MainScreenIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is ClickedOnMyBodyCard -> {
                _screenState.value = state.copy(navigateToMyBody = true)
            }

            is ClickedOnSeeAllExercises -> {
                _screenState.value = state.copy(seeAllExercises = true)
            }

            is ClickedOnExercise -> {
                _screenState.value = state.copy(navigateToTrainingType = intent.trainingType)
            }

            is ClickedOnSignOut -> {
                // Todo sign out
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(
                    navigateToTrainingType = null,
                    navigateToMyBody = false,
                    signOut = false,
                    seeAllExercises = false
                )
            }
        }
    }


}