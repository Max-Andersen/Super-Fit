package com.example.superfitcompose.ui.statistics

sealed class StatisticsIntent {

    object ClickedOnNavigateBack : StatisticsIntent()

    object NavigationProcessed : StatisticsIntent()
}
