package com.example.superfitcompose.ui.mybody

import android.net.Uri
import com.example.superfitcompose.ui.shared.models.PhotoData

data class MyBodyViewState (
    val weight: Int = 0,
    val height: Int = 0,
    val editWeight: Boolean = false,
    val editHeight: Boolean = false,
    val inputWeight: Int? = null,
    val inputHeight: Int? = null,

    val firstPhoto: PhotoData? = null,
    val latestPhoto: PhotoData? = null,

    val seeMyProgress: Boolean = false,

    val addImage: Boolean = false,
    val imageUri: Uri? = null,

    val seeTrainProgress: Boolean = false,
    val seeStatistics: Boolean = false
)