package com.example.superfitcompose.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val status: String,
    @SerializedName("status_code") val code: Int,
    val message: String,
    val timestamp: String
)
