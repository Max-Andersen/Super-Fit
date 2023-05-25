package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.toDomain
import com.example.superfitcompose.domain.models.BodyParameters
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetBodyParamsUseCase(private val profileRepositoryImpl: ProfileRepository) {
    operator fun invoke(): Flow<ApiResponse<List<BodyParameters>>> =
        profileRepositoryImpl.getBodyHistory().transform { response ->
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(response.data.map { it.toDomain() }))
                }

                else -> {
                    emit((response as? ApiResponse.Loading) ?: response as ApiResponse.Failure)
                }
            }
        }
}