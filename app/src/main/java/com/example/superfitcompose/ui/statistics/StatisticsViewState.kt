package com.example.superfitcompose.ui.statistics

import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.BodyParameters
import com.example.superfitcompose.domain.models.Training

data class StatisticsViewState(
    val weightHistory: List<BodyParameters>? = null,

    val selectedExercise: TrainingType = TrainingType.PUSH_UP,

    val pushUpsHistory: List<Training>? = null,
    val plankHistory: List<Training>? = null,
    val crunchHistory: List<Training>? = null,
    val squatsHistory: List<Training>? = null,
    val runningHistory: List<Training>? = null,

    val navigateBack: Boolean = false
)
