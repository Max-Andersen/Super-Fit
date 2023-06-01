package com.example.superfitcompose.ui.statistics

sealed class StatisticsIntent {
    object LoadData: StatisticsIntent()

    object SelectPushUpsHistory: StatisticsIntent()

    object SelectPlankHistory: StatisticsIntent()

    object SelectCrunchHistory: StatisticsIntent()

    object SelectSquatsHistory: StatisticsIntent()

    object SelectRunningHistory: StatisticsIntent()


    object ClickedOnNavigateBack : StatisticsIntent()

    object NavigationProcessed : StatisticsIntent()
}
