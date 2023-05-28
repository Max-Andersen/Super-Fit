package com.example.superfitcompose.ui.image

sealed class ImageIntent {
    data class LoadImage(val id: String, val date: String) : ImageIntent()

    object ClickedOnNavigateBack : ImageIntent()

    object NavigationProcessed : ImageIntent()
}
