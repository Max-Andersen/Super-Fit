package com.example.superfitcompose.data.network.api

import com.example.superfitcompose.data.network.models.TrainingDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TrainingApi {

    @GET("trainings")
    suspend fun getTrainings(): Response<List<TrainingDTO>>

    @POST("trainings")
    suspend fun saveTraining(@Body body: TrainingDTO): Response<TrainingDTO>
}