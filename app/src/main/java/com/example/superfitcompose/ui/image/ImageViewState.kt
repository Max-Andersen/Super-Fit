package com.example.superfitcompose.ui.image

import android.text.BoringLayout
import androidx.compose.ui.graphics.ImageBitmap

data class ImageViewState(
    val date: String? = null,
    val image: ImageBitmap? = null,
    val navigateBack: Boolean = false
)
