package com.example.superfitcompose.ui.exercise

import com.example.superfitcompose.data.network.models.TrainingType

sealed class ExerciseIntent{
    data class LoadExerciseData(val trainingType: TrainingType): ExerciseIntent()

    object StartExercise: ExerciseIntent()

    object PauseExercise: ExerciseIntent()

    object FinishExercise: ExerciseIntent()

    object ExerciseStepDone: ExerciseIntent()

}
