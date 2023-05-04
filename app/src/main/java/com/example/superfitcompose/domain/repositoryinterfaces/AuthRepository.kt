package com.example.superfitcompose.domain.repositoryinterfaces

import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.AccessTokenDTO
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.AuthResponseDTO
import com.example.superfitcompose.data.network.models.SimpleMessageDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getRefreshToken(data: AuthCredentialDTO): Flow<ApiResponse<AuthResponseDTO>>

    fun getAccessToken(data: RefreshTokenDTO): Flow<ApiResponse<AccessTokenDTO>>

    fun register(data: AuthCredentialDTO): Flow<ApiResponse<SimpleMessageDTO>>

}