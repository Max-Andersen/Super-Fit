package com.example.superfitcompose.ui.mybody

import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.IntentHandler
import com.example.superfitcompose.MyApplication
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.models.PhotoId
import com.example.superfitcompose.domain.usecases.AddNewImageUseCase
import com.example.superfitcompose.domain.usecases.DownloadPhotoUseCase
import com.example.superfitcompose.domain.usecases.GetBodyParamsUseCase
import com.example.superfitcompose.domain.usecases.GetUserPhotosUseCase
import com.example.superfitcompose.domain.usecases.UpdateBodyParamsUseCase
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnAddImage
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnSeeAllProgress
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnStatistics
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnTrainProgress
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnUpdateHeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClickedOnUpdateWeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.CloseEnterHeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.CloseEnterWeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.ClosePhotoSelect
import com.example.superfitcompose.ui.mybody.MyBodyIntent.EnterHeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.EnterWeight
import com.example.superfitcompose.ui.mybody.MyBodyIntent.LoadData
import com.example.superfitcompose.ui.mybody.MyBodyIntent.NavigationProcessed
import com.example.superfitcompose.ui.mybody.MyBodyIntent.NewImageSelected
import com.example.superfitcompose.ui.mybody.MyBodyIntent.SaveBodyParams
import com.example.superfitcompose.ui.mybody.MyBodyIntent.SaveNewImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn


class MyBodyViewModel(
    private val getBodyParamsUseCase: GetBodyParamsUseCase,
    private val updateBodyParamsUseCase: UpdateBodyParamsUseCase,
    private val addNewImageUseCase: AddNewImageUseCase,
    private val getUserPhotosUseCase: GetUserPhotosUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
    private val application: MyApplication
) : ViewModel(), IntentHandler<MyBodyIntent> {

    private val _screenState = MutableLiveData(MyBodyViewState())


    fun getViewState(): LiveData<MyBodyViewState> = _screenState

    override fun processIntent(intent: MyBodyIntent) {
        var state = _screenState.value ?: return

        when (intent) {
            is LoadData -> {
                viewModelScope.launch {
                    getBodyParamsUseCase().collect {
                        if (it is ApiResponse.Success){
                            withContext(Dispatchers.Main) {
                                val lastData = it.data.lastOrNull()

                                val height = lastData?.height ?: 0
                                val weight = lastData?.weight ?: 0

                                _screenState.value =
                                    state.copy(
                                        weight = weight,
                                        height = height,
                                        editWeight = height == 0,
                                        editHeight = weight == 0
                                    )
                                state = _screenState.value ?: return@withContext

                            }
                        }
                    }

                    getUserPhotosUseCase().collect {
                        var firstPhoto: PhotoId? = null
                        var latestPhoto: PhotoId? = null
                        when (it) {
                            is ApiResponse.Success -> {
                                val list = it.data.sortedBy { photo -> photo.uploaded }
                                firstPhoto = list.firstOrNull()
                                latestPhoto =
                                    if (firstPhoto != list.lastOrNull()) list.lastOrNull() else null
                            }

                            is ApiResponse.Failure -> {}
                            is ApiResponse.Loading -> {}
                        }

                        firstPhoto?.let { photo ->
                            downloadPhotoUseCase(photo.id).collect { image ->
                                if (image is ApiResponse.Success) {
                                    val bitmap = BitmapFactory.decodeByteArray(
                                        image.data.bytes(),
                                        0,
                                        image.data.contentLength().toInt()
                                    )
                                    withContext(Dispatchers.Main) {
                                        _screenState.value = state.copy(
                                            firstPhoto = PhotoData(
                                                Instant.fromEpochMilliseconds(photo.uploaded * 1000L).toString().subSequence(0, 10) as String,
                                                bitmap.asImageBitmap()
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        latestPhoto?.let { photo ->
                            downloadPhotoUseCase(photo.id).collect { image ->
                                if (image is ApiResponse.Success) {
                                    val bitmap = BitmapFactory.decodeByteArray(
                                        image.data.bytes(),
                                        0,
                                        image.data.contentLength().toInt()
                                    )
                                    withContext(Dispatchers.Main) {
                                        _screenState.value = state.copy(
                                            latestPhoto = PhotoData(
                                                Instant.fromEpochMilliseconds(photo.uploaded * 1000L).toString().subSequence(0, 10) as String,
                                                bitmap.asImageBitmap()
                                            )
                                        )
                                    }
                                }
                            }
                        }

                    }

                }
            }

            is ClickedOnUpdateWeight -> {
                _screenState.value = state.copy(editWeight = true)
            }

            is ClickedOnUpdateHeight -> _screenState.value = state.copy(editHeight = true)

            is EnterHeight -> {
                _screenState.value = state.copy(inputHeight = intent.height)
            }

            is EnterWeight -> {
                _screenState.value = state.copy(inputWeight = intent.weight)
            }

            is CloseEnterHeight -> {
                _screenState.value = state.copy(editHeight = false)
            }

            is CloseEnterWeight -> {
                _screenState.value = state.copy(editWeight = false)
            }

            is SaveBodyParams -> {
                if (((state.inputHeight ?: 0) > 0 || state.height > 0) && ((state.inputWeight
                        ?: 0) > 0 || state.weight > 0) // must be input value or value from server
                ) {

                    viewModelScope.launch {
                        updateBodyParamsUseCase(
                            state.inputWeight ?: state.weight,
                            state.inputHeight ?: state.height,
                            Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
                        ).collect {}
                    }

                    _screenState.value = state.copy(
                        weight = state.inputWeight ?: state.weight,
                        height = state.inputHeight ?: state.height
                    )
                    state = _screenState.value ?: return
                }
                if (intent.type == BodyParamsTypes.Weight) {
                    if (state.inputWeight != null) {
                        _screenState.value = state.copy(editWeight = false)
                    }
                } else {
                    if (state.inputHeight != null) {
                        _screenState.value = state.copy(editHeight = false)
                    }
                }

            }

            is ClickedOnSeeAllProgress -> {}

            is ClickedOnAddImage -> {
                _screenState.value = state.copy(addImage = true)
            }

            is NewImageSelected -> {
                _screenState.value = state.copy(imageUri = intent.uri)
            }

            is SaveNewImage -> {
                viewModelScope.launch {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(
                            application.contentResolver,
                            state.imageUri
                        )

                    addNewImageUseCase(bitmap).collect {
                        when (it) {
                            is ApiResponse.Success -> {
                                Log.d("!!!!", "SUCCEED")
                            }

                            is ApiResponse.Failure -> {
                                Log.d("!!!!", "FAIL")
                            }

                            is ApiResponse.Loading -> {}
                        }
                    }
                }
                _screenState.value = state.copy(imageUri = null, addImage = false)

            }

            is ClosePhotoSelect -> {
                _screenState.value = state.copy(addImage = false)
            }

            is ClickedOnTrainProgress -> {}

            is ClickedOnStatistics -> {}

            is NavigationProcessed -> {}
        }
    }

}