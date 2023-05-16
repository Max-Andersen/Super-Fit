package com.example.superfitcompose.ui.main.exercises

import com.example.superfitcompose.data.network.models.TrainingType

sealed class AllExercisesIntent {
    data class ClickedOnExercise(val trainingType: TrainingType) : AllExercisesIntent()
    object NavigationProcessed: AllExercisesIntent()

}
