package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.ui.trainprogress.TrainProgress

class GetTrainProgressForExercise {
    operator fun invoke(trainings: List<Training>): TrainProgress {
        val pushUpProgress = ((trainings.lastOrNull()?.repeatCount?.toFloat()
            ?: 0f) - (trainings.dropLast(1).lastOrNull()?.repeatCount?.toFloat()
            ?: 0f)) / (trainings.dropLast(1).lastOrNull()?.repeatCount?.toFloat()
            ?: 1f)

        return TrainProgress(
            lastTrain = trainings.lastOrNull()?.repeatCount ?: 0,
            progress = (pushUpProgress * 100f).toInt(),
        )
    }
}