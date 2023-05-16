package com.example.superfitcompose.ui.main.mainscreen

import com.example.superfitcompose.data.network.models.TrainingType

data class MainScreenViewState(
    val navigateToMyBody: Boolean = false,
    val seeAllExercises: Boolean = false,
    val navigateToTrainingType: TrainingType? = null,
    val signOut: Boolean = false
)
