package com.example.superfitcompose.ui.image

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.usecases.DownloadPhotoUseCase
import com.example.superfitcompose.ui.image.ImageIntent.ClickedOnNavigateBack
import com.example.superfitcompose.ui.image.ImageIntent.LoadImage
import com.example.superfitcompose.ui.image.ImageIntent.NavigationProcessed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageViewModel(
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
) : ViewModel(), IntentHandler<ImageIntent> {
    private val _screenState = MutableLiveData(ImageViewState())

    fun getViewState(): LiveData<ImageViewState> = _screenState

    override fun processIntent(intent: ImageIntent) {
        val state = _screenState.value ?: return
        when (intent) {
            is LoadImage -> {
                viewModelScope.launch {
                    downloadPhotoUseCase(intent.id).collect { image ->
                        if (image is ApiResponse.Success) {
                            withContext(Dispatchers.Main) {
                                val bitmap = BitmapFactory.decodeByteArray(
                                    image.data.bytes(),
                                    0,
                                    image.data.contentLength().toInt()
                                )
                                _screenState.value =
                                    state.copy(image = bitmap.asImageBitmap(), date = intent.date)
                            }
                        }
                    }
                }
            }

            is ClickedOnNavigateBack -> {
                _screenState.value = state.copy(navigateBack = true)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateBack = false)
            }
        }
    }
}