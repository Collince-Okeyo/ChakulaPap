package com.ramgdev.chakulapap

import android.app.Application
import timber.log.Timber

class ChakulaPapApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}