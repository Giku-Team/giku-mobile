package com.mobile.giku.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GikuApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GikuApp)
            modules(appModules, networkModules)
        }
    }
}