package com.example.superfitcompose.ui.exercise

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ExerciseViewState(
    val launchedMessage: Boolean = true,
    val error: Int? = null,

    val pause: Boolean = true,
    val finished: Boolean = false,

    val counter: Int = 0,
    val beginCounterValue: Int = 0,
    val saved: Boolean = false
)
