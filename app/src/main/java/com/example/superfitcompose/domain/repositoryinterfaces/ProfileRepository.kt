package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.BodyParametersDTO
import com.example.superfitcompose.data.network.models.LoginDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.PhotoIdDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody

interface ProfileRepository {

    fun getProfileLogin(): Flow<ApiResponse<LoginDTO>>

    fun updateBodyParams(newParams: BodyParametersDTO): Flow<ApiResponse<SimpleMessageDTO>>

    fun getBodyHistory(): Flow<ApiResponse<List<BodyParametersDTO>>>

    fun getBodyPhotoIds(): Flow<ApiResponse<List<PhotoIdDTO>>>

    fun uploadBodyPhoto(image: MultipartBody.Part): Flow<ApiResponse<PhotoIdDTO>>

    fun downloadBodyPhoto(id : String): Flow<ApiResponse<ResponseBody>>

    fun removeBodyPhoto(id : String): Flow<ApiResponse<SimpleMessageDTO>>

}