package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.BodyParametersDTO
import com.example.superfitcompose.data.network.models.LoginDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.PhotoIdDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {

    fun GetProfileLogin(): Flow<ApiResponse<LoginDTO>>

    fun UpdateBodyParams(newParams: BodyParametersDTO): Flow<ApiResponse<SimpleMessageDTO>>

    fun GetBodyHistory(): Flow<ApiResponse<List<BodyParametersDTO>>>

    fun GetBodyPhotoIds(): Flow<ApiResponse<List<PhotoIdDTO>>>

    fun UploadBodyPhoto(image: MultipartBody.Part): Flow<ApiResponse<PhotoIdDTO>>

    fun DownloadBodyPhoto(id : String): Flow<ApiResponse<MultipartBody.Part>>

    fun RemoveBodyPhoto(id : String): Flow<ApiResponse<SimpleMessageDTO>>

}