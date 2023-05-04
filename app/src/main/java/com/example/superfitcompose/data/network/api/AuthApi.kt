package com.example.superfitcompose.data.network.api

import com.example.superfitcompose.data.network.models.AccessTokenDTO
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.AuthResponseDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/token")
    suspend fun getRefreshToken(@Body body: AuthCredentialDTO): Response<AuthResponseDTO>

    @POST("auth/token/refresh")
    suspend fun getAccessToken(@Body body: RefreshTokenDTO): Response<AccessTokenDTO>

    @POST("auth/register")
    suspend fun register(@Body body: AuthCredentialDTO): Response<SimpleMessageDTO>
}