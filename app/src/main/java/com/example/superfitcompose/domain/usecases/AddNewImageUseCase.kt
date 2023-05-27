package com.example.superfitcompose.domain.usecases

import android.graphics.Bitmap
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.data.network.models.toDomain
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import kotlinx.coroutines.flow.transform
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class AddNewImageUseCase(private val profileRepository: ProfileRepository) {

    operator fun invoke(bitmap: Bitmap) =
        profileRepository.uploadBodyPhoto(convertImageFileToRequestBody(bitmap)).transform {response ->
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(response.data.toDomain()))
                }

                else -> {
                    emit((response as? ApiResponse.Loading) ?: response as ApiResponse.Failure)
                }
            }
        }


    private fun convertImageFileToRequestBody(bitmap: Bitmap): MultipartBody.Part {

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        return MultipartBody.Part.createFormData(
            "file", "photo.png",
            byteArray.toRequestBody("image/png".toMediaTypeOrNull(), 0, byteArray.size)
        )
    }
}