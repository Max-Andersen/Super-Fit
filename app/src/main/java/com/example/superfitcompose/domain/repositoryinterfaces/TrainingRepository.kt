package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.TrainingDTO
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    fun GetTrainings(): Flow<ApiResponse<List<TrainingDTO>>>

    fun SaveTraining(training: TrainingDTO): Flow<ApiResponse<TrainingDTO>>

}