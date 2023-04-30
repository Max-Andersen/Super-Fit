package com.example.superfitcompose.data.network.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*

class MyAuthenticator : Authenticator {


    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount >= 5){
            runBlocking {
                withContext(Dispatchers.Main) {

                }
            }
            return null
        }

//        val refreshToken = sharedPreferencesUseCase.getRefreshToken()
//        if (refreshToken != "") {
//                val newTokenResponse = runBlocking { getNewToken(refreshToken) }
//
//                if (newTokenResponse.isSuccessful) {
//                    newTokenResponse.body()?.let {
//                        sharedPreferencesUseCase.updateAccessToken(it.accessToken)
//                        sharedPreferencesUseCase.updateRefreshToken(it.refreshToken)
//
//                        return response.request.newBuilder()
//                            .header("Authorization", "Bearer ${it.accessToken}")
//                            .build()
//                    }
//                } else {
//                    return response.request.newBuilder()
//                        .header("Authorization", "Bearer ${sharedPreferencesUseCase.getAccessToken()}")
//                        .build()
//                }
//
//        }

        return null
    }


    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()

//    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<AuthResponse> {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(com.example.mobilecinemalab.datasource.network.Network.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//        val authApi = retrofit.create(AuthApi::class.java)
//
//
//
//        return authApi.refresh("Bearer $refreshToken")
//    }

}