package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.AccessTokenDTO
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.AuthResponseDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun GetRefreshToken(data: AuthCredentialDTO): Flow<ApiResponse<AuthResponseDTO>>

    fun GetAccessToken(data: RefreshTokenDTO): Flow<ApiResponse<AccessTokenDTO>>

    fun Register(data: AuthCredentialDTO): Flow<ApiResponse<SimpleMessageDTO>>

}