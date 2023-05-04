package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.ProfileApi
import com.example.superfitcompose.data.network.models.BodyParametersDTO
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import okhttp3.MultipartBody

class ProfileRepositoryImpl: ProfileRepository, BaseRepository() {
    private val profileApi: ProfileApi = Network.getProfileApi()

    override fun getProfileLogin() = apiRequestFlow { profileApi.getProfile() }

    override fun updateBodyParams(newParams: BodyParametersDTO) = apiRequestFlow { profileApi.updateBodyParams(newParams) }

    override fun getBodyHistory() = apiRequestFlow { profileApi.getBodyHistory() }

    override fun getBodyPhotoIds() = apiRequestFlow { profileApi.getBodyPhotoIds() }

    override fun uploadBodyPhoto(image: MultipartBody.Part) = apiRequestFlow { profileApi.uploadBodyPhoto(image) }

    override fun downloadBodyPhoto(id: String) = apiRequestFlow { profileApi.downloadBodyPhoto(id) }

    override fun removeBodyPhoto(id: String) = apiRequestFlow { profileApi.removeBodyPhoto(id) }
}