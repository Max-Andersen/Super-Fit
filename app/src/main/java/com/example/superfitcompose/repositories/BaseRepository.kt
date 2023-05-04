package com.example.superfitcompose.repositories

import com.example.superfitcompose.data.network.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.Response

abstract class BaseRepository {
    protected fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)

        withTimeoutOrNull(20000L) {
            val response = call()

            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        emit(ApiResponse.Success(data))
                    }

                } else {

                    response.errorBody()?.let { error ->
//                        val parsedError: ErrorMessage =
//                            Gson().fromJson(error.charStream(), ErrorMessage::class.java)
//                        emit(ApiResponse.Failure(parsedError.message.orEmpty(), parsedError.code))


                        emit(ApiResponse.Failure("", response.code()))

                        error.close()
                    }

                }
            } catch (e: Exception) {
                emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
            }
        } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
    }.flowOn(Dispatchers.IO)
}