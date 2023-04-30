package com.example.superfitcompose.data.network.retrofit


import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request.Builder = chain.request().newBuilder().apply {
            addHeader("accept", "application/json")
        }

        //val accessToken = SharedPreferencesUseCase().getAccessToken()
//        if (accessToken != ""){
//            request.addHeader("Authorization", "Bearer $accessToken")
//        }

        return chain.proceed(request.build())
    }
}