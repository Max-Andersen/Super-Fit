package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.toDomain
import com.example.superfitcompose.domain.models.Training
import com.example.superfitcompose.domain.repositoryinterfaces.TrainingRepository
import com.example.superfitcompose.repositories.TrainingRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetTrainingHistoryUseCase {
    private val trainingRepository: TrainingRepository = TrainingRepositoryImpl()

    operator fun invoke(): Flow<ApiResponse<List<Training>>> =
        trainingRepository.getTrainings().transform { response ->
            when (response) {
                is ApiResponse.Success -> emit(ApiResponse.Success(response.data.map { it.toDomain() }))

                is ApiResponse.Loading -> emit(response)
                is ApiResponse.Failure -> emit(response)
            }
        }
}