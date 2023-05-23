package com.example.superfitcompose

import android.app.Application
import android.content.Context
import com.example.superfitcompose.di.data
import com.example.superfitcompose.di.repositories
import com.example.superfitcompose.di.retrofit
import com.example.superfitcompose.di.usecases
import com.example.superfitcompose.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(data, viewModels, usecases, repositories, retrofit)
        }
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}