package com.example.superfitcompose.data.network

import com.example.superfitcompose.data.network.api.AuthApi
import com.example.superfitcompose.data.network.api.ProfileApi
import com.example.superfitcompose.data.network.api.TrainingApi
import com.example.superfitcompose.data.network.retrofit.MyAuthenticator
import com.example.superfitcompose.data.network.retrofit.MyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    const val BASE_URL = "http://fitness.wsmob.xyz:22169/api/"

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(MyInterceptor())
            authenticator(MyAuthenticator())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)

    fun getProfileApi(): ProfileApi = retrofit.create(ProfileApi::class.java)

    fun getTrainingApi(): TrainingApi = retrofit.create(TrainingApi::class.java)
}