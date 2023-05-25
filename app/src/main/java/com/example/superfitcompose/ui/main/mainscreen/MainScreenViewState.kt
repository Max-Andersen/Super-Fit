package com.example.superfitcompose.ui.main.mainscreen

import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.BodyParameters

data class MainScreenViewState(
    val bodyParams: BodyParameters? = null,
    val lastTrainings: Pair<TrainingType?, TrainingType?>? = null,
    val navigateToMyBody: Boolean = false,
    val seeAllExercises: Boolean = false,
    val navigateToTrainingType: TrainingType? = null,
    val signOut: Boolean = false
)
