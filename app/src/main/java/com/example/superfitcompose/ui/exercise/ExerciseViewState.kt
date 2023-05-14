package com.example.superfitcompose.ui.exercise

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ExerciseViewState(
    val pause: Boolean = true,

    val finished: Boolean = false,

    val counter: Int = 0,
    val beginCounterValue: Int = 0,
)
