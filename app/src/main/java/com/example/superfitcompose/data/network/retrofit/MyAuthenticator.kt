package com.example.superfitcompose.data.network.retrofit

import com.example.superfitcompose.data.network.api.AuthApi
import com.example.superfitcompose.data.network.models.AccessTokenDTO
import com.example.superfitcompose.data.network.models.AuthCredentialDTO
import com.example.superfitcompose.data.network.models.AuthResponseDTO
import com.example.superfitcompose.data.network.models.RefreshTokenDTO
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAuthenticator(
    private val sharedPreferences: SharedPreferencesInteractor,
    private val baseUrl: String
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount >= 5) {
            runBlocking {
                withContext(Dispatchers.Main) {
                    // Todo trouble shooting
                }
            }
            return null
        }

        val refreshToken = sharedPreferences.getRefreshToken()
        if (refreshToken != "") {
            val newTokenResponse = runBlocking { getNewAccessToken(refreshToken) }

            if (newTokenResponse.isSuccessful) {
                newTokenResponse.body()?.let { accessToken ->
                    sharedPreferences.updateAccessToken(accessToken.accessToken)
                    return requestWithAccessToken(response, accessToken.accessToken)
                }
            } else {

                val login = sharedPreferences.getUserLogin()
                val password = sharedPreferences.getUserPassword()

                val getRefreshTokenResponse = runBlocking { getNewRefreshToken(login, password) }

                if (getRefreshTokenResponse.isSuccessful) {
                    getRefreshTokenResponse.body()?.let { authResponse ->
                        sharedPreferences.updateRefreshToken(authResponse.refreshToken)

                        val access = runBlocking { getNewAccessToken(refreshToken) }

                        if (access.isSuccessful) {
                            access.body()?.let {
                                sharedPreferences.updateAccessToken(it.accessToken)
                                return requestWithAccessToken(response, it.accessToken)
                            }
                        } else {
                            return requestWithAccessToken(response)
                        }


                    }
                } else {
                    return requestWithAccessToken(response)
                }
            }
        }

        return null
    }


    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()

    private fun requestWithAccessToken(
        response: Response,
        token: String = sharedPreferences.getAccessToken()
    ): Request {
        return response.request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
    }


    private suspend fun getNewAccessToken(refreshToken: String): retrofit2.Response<AccessTokenDTO> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val authApi = retrofit.create(AuthApi::class.java)



        return authApi.getAccessToken(RefreshTokenDTO(refreshToken = refreshToken))
    }

    private suspend fun getNewRefreshToken(
        login: String,
        password: String
    ): retrofit2.Response<AuthResponseDTO> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val authApi = retrofit.create(AuthApi::class.java)



        return authApi.getRefreshToken(AuthCredentialDTO(login, password))
    }

}