package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.ui.trainprogress.TrainProgress

class GetTrainProgressForExercise {
    operator fun invoke(trainings: List<Training>): TrainProgress {
        var progress = ((trainings.lastOrNull()?.repeatCount?.toFloat()
            ?: 0f) - (trainings.dropLast(1).lastOrNull()?.repeatCount?.toFloat()
            ?: 0f)) / (trainings.dropLast(1).lastOrNull()?.repeatCount?.toFloat()
            ?: 1f)

        if (trainings.size == 1){
            progress = 1f
        }

        return TrainProgress(
            lastTrain = trainings.lastOrNull()?.repeatCount ?: 0,
            progress = (progress * 100f).toInt(),
        )
    }
}