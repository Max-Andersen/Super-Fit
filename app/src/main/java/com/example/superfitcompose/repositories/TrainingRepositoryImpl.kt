package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.TrainingApi
import com.example.superfitcompose.data.network.models.TrainingDTO
import com.example.superfitcompose.domain.repositoryinterfaces.TrainingRepository

class TrainingRepositoryImpl: TrainingRepository, BaseRepository() {
    private val trainingApi: TrainingApi = Network.getTrainingApi()

    override fun GetTrainings() = apiRequestFlow { trainingApi.getTrainings() }

    override fun SaveTraining(training: TrainingDTO) = apiRequestFlow { trainingApi.saveTraining(training) }

}