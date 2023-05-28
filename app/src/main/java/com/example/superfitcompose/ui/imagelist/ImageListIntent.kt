package com.example.superfitcompose.ui.imagelist

import com.example.superfitcompose.ui.shared.models.PhotoData

sealed class ImageListIntent{
    object LoadData: ImageListIntent()

    data class ClickedOnImage(val data: PhotoData) : ImageListIntent()

    object NavigationBack: ImageListIntent()

    object NavigationProcessed: ImageListIntent()

}
