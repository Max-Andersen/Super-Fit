package com.example.superfitcompose.ui.mybody

sealed class MyBodyIntent{
    object GetBodyParams: MyBodyIntent()

    object GetLastImages: MyBodyIntent()

    object ClickedOnUpdateWeight: MyBodyIntent()

    object ClickedOnUpdateHeight: MyBodyIntent()

    data class EnterHeight(val height: Int): MyBodyIntent()

    data class EnterWeight(val weight: Int): MyBodyIntent()

    object ClickedOnSeeAllProgress: MyBodyIntent()

    object ClickedOnAddImage: MyBodyIntent()

    object ClickedOnTrainProgress: MyBodyIntent()

    object ClickedOnStatistics: MyBodyIntent()

    object NavigationProcessed: MyBodyIntent()
}
