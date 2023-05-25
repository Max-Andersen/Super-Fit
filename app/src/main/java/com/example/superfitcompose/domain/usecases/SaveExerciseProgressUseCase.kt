package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.data.network.models.TrainingType
import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.TrainingRepository
import com.example.superfitcompose.repositories.TrainingRepositoryImpl

class SaveExerciseProgressUseCase(private val trainingRepository: TrainingRepository) {
    operator fun invoke(trainingType: TrainingType, count: Int, currentDate: String) =
        trainingRepository.saveTraining(Training(currentDate, trainingType, count).fromDomain())
}