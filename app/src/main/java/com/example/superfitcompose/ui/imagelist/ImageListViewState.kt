package com.example.superfitcompose.ui.imagelist

import com.example.superfitcompose.ui.shared.models.PhotoData

data class ImageListViewState (
    val imageList: Map<String, List<PhotoData>>? = null,
    val navigateBack: Boolean = false,
    val navigateToPhotoData: PhotoData? = null,
)