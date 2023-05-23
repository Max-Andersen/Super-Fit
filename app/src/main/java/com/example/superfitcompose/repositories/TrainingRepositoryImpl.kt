package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.TrainingApi
import com.example.superfitcompose.data.network.models.TrainingDTO
import com.example.superfitcompose.domain.repositoryinterfaces.TrainingRepository

class TrainingRepositoryImpl(network: Network): TrainingRepository, BaseRepository() {
    private val trainingApi: TrainingApi = network.getTrainingApi()

    override fun getTrainings() = apiRequestFlow { trainingApi.getTrainings() }

    override fun saveTraining(training: TrainingDTO) = apiRequestFlow { trainingApi.saveTraining(training) }

}