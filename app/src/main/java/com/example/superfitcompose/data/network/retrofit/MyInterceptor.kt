package com.example.superfitcompose.data.network.retrofit


import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor(private val sharedPreferencesInteractor: SharedPreferencesInteractor) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
        }

        val accessToken = sharedPreferencesInteractor.getAccessToken()
        if (accessToken != "") {
            request.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(request.build())
    }
}