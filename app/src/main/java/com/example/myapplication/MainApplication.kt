package com.example.myapplication

import android.app.Application
import timber.log.Timber


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Timberを有効にします
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}
