package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingDTO
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    fun getTrainings(): Flow<ApiResponse<List<TrainingDTO>>>

    fun saveTraining(training: TrainingDTO): Flow<ApiResponse<TrainingDTO>>

}