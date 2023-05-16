package com.example.superfitcompose.ui.main.mainscreen

import com.example.superfitcompose.ui.main.Exercise

data class MainScreenViewState(
    val navigateToMyBody: Boolean = false,
    val seeAllExercises: Boolean = false,
    val navigateToExercise: Exercise? = null,
    val signOut: Boolean = false
)
