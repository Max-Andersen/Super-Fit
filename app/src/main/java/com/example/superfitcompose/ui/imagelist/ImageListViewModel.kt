package com.example.superfitcompose.ui.imagelist

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.models.PhotoId
import com.example.superfitcompose.domain.usecases.DownloadPhotoUseCase
import com.example.superfitcompose.domain.usecases.GetUserPhotosUseCase
import com.example.superfitcompose.ui.imagelist.ImageListIntent.ClickedOnImage
import com.example.superfitcompose.ui.imagelist.ImageListIntent.LoadData
import com.example.superfitcompose.ui.imagelist.ImageListIntent.NavigationBack
import com.example.superfitcompose.ui.imagelist.ImageListIntent.NavigationProcessed
import com.example.superfitcompose.ui.shared.models.PhotoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant


class ImageListViewModel(
    private val getUserPhotosUseCase: GetUserPhotosUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
) : ViewModel(), IntentHandler<ImageListIntent> {

    private val _screenState = MutableLiveData(ImageListViewState())

    fun getViewState(): LiveData<ImageListViewState> = _screenState

    override fun processIntent(intent: ImageListIntent) {
        val state = _screenState.value ?: return

        when (intent) {
            is LoadData -> {
                viewModelScope.launch {
                    var listOfPhotoId = listOf<PhotoId>()
                    getUserPhotosUseCase().collect {
                        if (it is ApiResponse.Success) {
                            listOfPhotoId = it.data
                        }
                    }

                    if (listOfPhotoId.isNotEmpty()) {
                        val photoList = mutableListOf<PhotoData>()

                        listOfPhotoId.forEach { photoId ->
                            downloadPhotoUseCase(photoId.id).collect { image ->
                                if (image is ApiResponse.Success) {
                                    val bitmap = BitmapFactory.decodeByteArray(
                                        image.data.bytes(),
                                        0,
                                        image.data.contentLength().toInt()
                                    )

                                    photoList.add(
                                        PhotoData(
                                            Instant.fromEpochMilliseconds(photoId.uploaded * 1000L)
                                                .toString().subSequence(0, 10) as String,
                                            bitmap.asImageBitmap()
                                        )
                                    )
                                }
                            }
                        }

                        val data = mutableMapOf<String, MutableList<PhotoData>>()

                        var lastYear = photoList.firstOrNull()?.date?.substring(0, 4)
                        var lastMonth = photoList.firstOrNull()?.date?.substring(5, 7)


                        photoList.sortedBy { item -> item.date }.forEach { photoData ->
                            val photoYear = photoData.date.substring(0, 4)
                            val photoMonth = photoData.date.substring(5, 7)
                            if (photoYear == lastYear){
                                if (photoMonth == lastMonth){
                                    if (data["$lastYear $lastMonth"] == null){
                                        data["$lastYear $lastMonth"] = mutableListOf(photoData)
                                    } else{
                                        data["$lastYear $lastMonth"]?.add(photoData)
                                    }
                                    lastYear = photoYear
                                    lastMonth = photoMonth
                                } else{
                                    data[photoYear + photoMonth] = mutableListOf(photoData)
                                    lastYear = photoYear
                                    lastMonth = photoMonth
                                }
                            } else{
                                data[photoYear + photoMonth] = mutableListOf(photoData)
                                lastYear = photoYear
                                lastMonth = photoMonth
                            }
                        }

                        withContext(Dispatchers.Main) {
                            _screenState.value =
                                state.copy(imageList = data)
                        }
                    }

                }
            }

            is ClickedOnImage -> {

            }

            is NavigationBack -> {
                _screenState.value = state.copy(navigateBack = true)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(navigateBack = false, navigateToPhotoData = null)
            }
        }
    }

}