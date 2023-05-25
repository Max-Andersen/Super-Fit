package com.example.superfitcompose.ui.mybody

data class MyBodyViewState (
    val weight: Int? = null,
    val height: Int? = null,
    val editWeight: Boolean = false,
    val editHeight: Boolean = false,
    val inputWeight: Int? = null,
    val inputHeight: Int? = null,

    val seeMyProgress: Boolean = false,
    val addImage: Boolean = false,


    val seeTrainProgress: Boolean = false,
    val seeStatistics: Boolean = false
)