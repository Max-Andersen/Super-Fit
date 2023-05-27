package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository

class DownloadPhotoUseCase(private val profileRepository: ProfileRepository) {

    operator fun invoke(photoId: String) = profileRepository.downloadBodyPhoto(photoId)
}