package com.example.superfitcompose.domain.usecases

import android.util.Log
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.BothTokens
import com.example.superfitcompose.domain.models.RefreshToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

class GetTokensUseCase(private val login: String, private val code: String) {

    suspend operator fun invoke(): ApiResponse<BothTokens> {
        GetRefreshTokenUseCase(AuthCredential(login, code))().last().let { refresh ->
            when (refresh) {
                is ApiResponse.Loading -> {}
                is ApiResponse.Success -> {



                    GetAccessTokenUseCase(RefreshToken(refresh.data.refreshToken))().last().let { access ->
                        when (access) {
                            is ApiResponse.Loading -> {}
                            is ApiResponse.Success -> {
                                return ApiResponse.Success(BothTokens(access.data.accessToken, refresh.data.refreshToken))
                            }

                            is ApiResponse.Failure -> {
                                return ApiResponse.Failure(access.errorMessage, access.code)
                            }
                        }
                    }


                }

                is ApiResponse.Failure -> {
                    return ApiResponse.Failure(refresh.errorMessage, refresh.code)
                }
            }
        }

        return ApiResponse.Failure("Unexpected error", 400)

    }
}