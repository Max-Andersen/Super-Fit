package com.example.superfitcompose.ui.main.mainscreen

import com.example.superfitcompose.ui.main.Exercise

sealed class MainScreenIntent {
    object ClickedOnMyBodyCard: MainScreenIntent()
    object ClickedOnSeeAllExercises: MainScreenIntent()

    data class ClickedOnExercise(val exercise: Exercise): MainScreenIntent()

    object ClickedOnSignOut: MainScreenIntent()

    object NavigationProcessed: MainScreenIntent()

}