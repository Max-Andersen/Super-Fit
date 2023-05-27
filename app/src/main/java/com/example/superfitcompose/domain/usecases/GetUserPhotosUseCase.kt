package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.toDomain
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import kotlinx.coroutines.flow.transform

class GetUserPhotosUseCase(private val profileRepository: ProfileRepository) {

    operator fun invoke() = profileRepository.getBodyPhotoIds().transform {response ->
        when (response) {
            is ApiResponse.Success -> emit(ApiResponse.Success(response.data.map { it.toDomain() }))

            is ApiResponse.Loading -> emit(response)
            is ApiResponse.Failure -> emit(response)
        }
    }
}