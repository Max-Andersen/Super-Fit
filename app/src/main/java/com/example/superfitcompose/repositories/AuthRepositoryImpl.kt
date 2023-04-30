package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.api.AuthApi
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository

class AuthRepositoryImpl: AuthRepository, BaseRepository() {

    private val authApi: AuthApi = Network.getAuthApi()

    override fun GetRefreshToken(data: AuthCredentialDTO) = apiRequestFlow { authApi.GetRefreshToken(data) }

    override fun GetAccessToken(data: RefreshTokenDTO) = apiRequestFlow { authApi.GetAccessToken(data) }

    override fun Register(data: AuthCredentialDTO) = apiRequestFlow { authApi.Register(data) }

}