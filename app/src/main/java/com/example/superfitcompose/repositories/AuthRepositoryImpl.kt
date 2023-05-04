package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.AuthApi
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository

class AuthRepositoryImpl: AuthRepository, BaseRepository() {

    private val authApi: AuthApi = Network.getAuthApi()

    override fun getRefreshToken(data: AuthCredentialDTO) = apiRequestFlow { authApi.getRefreshToken(data) }

    override fun getAccessToken(data: RefreshTokenDTO) = apiRequestFlow { authApi.getAccessToken(data) }

    override fun register(data: AuthCredentialDTO) = apiRequestFlow { authApi.register(data) }

}