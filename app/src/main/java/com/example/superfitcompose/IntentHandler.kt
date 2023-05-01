package com.example.superfitcompose

interface IntentHandler<T> {
    fun processIntent(intent: T)
}