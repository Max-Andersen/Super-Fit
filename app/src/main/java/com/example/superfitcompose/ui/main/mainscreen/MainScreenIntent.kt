package com.example.superfitcompose.ui.main.mainscreen

import com.example.superfitcompose.data.network.models.TrainingType

sealed class MainScreenIntent {

    object GetBodyParams: MainScreenIntent()

    object GetLastTraining: MainScreenIntent()

    object ClickedOnMyBodyCard: MainScreenIntent()

    object ClickedOnSeeAllExercises: MainScreenIntent()

    data class ClickedOnExercise(val trainingType: TrainingType): MainScreenIntent()

    object ClickedOnSignOut: MainScreenIntent()

    object NavigationProcessed: MainScreenIntent()

}