package com.example.superfitcompose.ui.main.exercises

import com.example.superfitcompose.ui.main.Exercise

sealed class AllExercisesIntent {
    data class ClickedOnExercise(val exercise: Exercise) : AllExercisesIntent()
    object NavigationProcessed: AllExercisesIntent()

}
