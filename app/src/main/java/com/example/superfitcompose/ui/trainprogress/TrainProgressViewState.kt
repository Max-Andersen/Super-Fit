package com.example.superfitcompose.ui.trainprogress

data class TrainProgressViewState(
    val pushUpsTrainProgress: TrainProgress? = null,
    val plankTrainProgress: TrainProgress? = null,
    val crunchTrainProgress: TrainProgress? = null,
    val squatsTrainProgress: TrainProgress? = null,
    val runningTrainProgress: TrainProgress? = null,
    val navigateBack: Boolean = false,
)
