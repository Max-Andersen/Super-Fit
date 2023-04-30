package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.ProfileApi
import com.example.superfitcompose.data.network.models.BodyParametersDTO
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import okhttp3.MultipartBody

class ProfileRepositoryImpl: ProfileRepository, BaseRepository() {
    private val profileApi: ProfileApi = Network.getProfileApi()

    override fun GetProfileLogin() = apiRequestFlow { profileApi.GetProfile() }

    override fun UpdateBodyParams(newParams: BodyParametersDTO) = apiRequestFlow { profileApi.UpdateBodyParams(newParams) }

    override fun GetBodyHistory() = apiRequestFlow { profileApi.GetBodyHistory() }

    override fun GetBodyPhotoIds() = apiRequestFlow { profileApi.GetBodyPhotoIds() }

    override fun UploadBodyPhoto(image: MultipartBody.Part) = apiRequestFlow { profileApi.UploadBodyPhoto(image) }

    override fun DownloadBodyPhoto(id: String) = apiRequestFlow { profileApi.DownloadBodyPhoto(id) }

    override fun RemoveBodyPhoto(id: String) = apiRequestFlow { profileApi.RemoveBodyPhoto(id) }
}