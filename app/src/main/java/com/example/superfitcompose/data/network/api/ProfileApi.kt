package com.example.superfitcompose.data.network.api

import com.example.superfitcompose.data.network.models.BodyParametersDTO
import com.example.superfitcompose.data.network.models.LoginDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.PhotoIdDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProfileApi {

    @GET("profile")
    fun GetProfile(): Response<LoginDTO>

    @POST("profile/params")
    fun UpdateBodyParams(@Body body: BodyParametersDTO): Response<SimpleMessageDTO>

    @GET("profile/params/history")
    fun GetBodyHistory(): Response<List<BodyParametersDTO>>

    @GET("profile/photos")
    fun GetBodyPhotoIds(): Response<List<PhotoIdDTO>>

    @Multipart
    @GET("profile/photos")
    fun UploadBodyPhoto(@Part image: MultipartBody.Part): Response<PhotoIdDTO>

    @Multipart
    @GET("profile/photos/{id}")
    fun DownloadBodyPhoto(@Path("id") id : String): Response<MultipartBody.Part>

    @GET("profile/photos/{id}")
    fun RemoveBodyPhoto(@Path("id") id : String): Response<SimpleMessageDTO>

}