package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.BodyParameters
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository

class UpdateBodyParamsUseCase(private val profileRepository: ProfileRepository) {

    operator fun invoke(weight: Int, height: Int, date: String) =
        profileRepository.updateBodyParams(BodyParameters(weight, height, date).fromDomain())
}