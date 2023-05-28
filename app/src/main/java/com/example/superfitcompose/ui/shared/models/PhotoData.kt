package com.example.superfitcompose.ui.shared.models

import androidx.compose.ui.graphics.ImageBitmap


data class PhotoData (
    val date: String,
    val id: String,
    val photo: ImageBitmap,
)