package com.example.superfitcompose.ui.mybody

import android.net.Uri

sealed class MyBodyIntent{
    object LoadData: MyBodyIntent()

    object ClickedOnUpdateWeight: MyBodyIntent()

    object ClickedOnUpdateHeight: MyBodyIntent()

    data class EnterHeight(val height: Int): MyBodyIntent()

    data class EnterWeight(val weight: Int): MyBodyIntent()

    object CloseEnterHeight: MyBodyIntent()

    object CloseEnterWeight: MyBodyIntent()

    data class SaveBodyParams(val type: BodyParamsTypes): MyBodyIntent()

    object ClickedOnSeeAllProgress: MyBodyIntent()


    object ClickedOnAddImage: MyBodyIntent()

    data class NewImageSelected(val uri: Uri?): MyBodyIntent()

    object SaveNewImage: MyBodyIntent()

    object ClosePhotoSelect: MyBodyIntent()

    object ClickedOnTrainProgress: MyBodyIntent()

    object ClickedOnStatistics: MyBodyIntent()

    object NavigationProcessed: MyBodyIntent()
}
