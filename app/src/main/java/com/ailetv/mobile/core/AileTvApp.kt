package com.ailetv.mobile.core

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.ailetv.mobile.BuildConfig
import com.ailetv.mobile.di.appModule
import com.ailetv.mobile.manager.SessionManager
import com.ailetv.mobile.utils.DeviceUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class AileTvApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        DeviceUtil.init(applicationContext)
        SessionManager.init(applicationContext)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@AileTvApp)
            modules(listOf(appModule))
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}