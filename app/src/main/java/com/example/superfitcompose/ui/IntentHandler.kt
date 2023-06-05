package com.example.superfitcompose.ui

interface IntentHandler<T> {
    fun processIntent(intent: T)
}