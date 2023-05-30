package com.example.superfitcompose.ui.trainprogress

sealed class TrainProgressIntent {
    object LoadData: TrainProgressIntent()

    object ClickedOnNavigateBack : TrainProgressIntent()

    object NavigationProcessed : TrainProgressIntent()
}
