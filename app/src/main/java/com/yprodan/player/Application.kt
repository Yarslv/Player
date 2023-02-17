package com.yprodan.player

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.LogLevel
import com.yprodan.player.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    private val appModules by lazy {
        listOf(mapperModule, repositoryModule, providerModule, viewModelModule, networkModule)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(appModules)
        }

        val appToken = ""
        val environment = AdjustConfig.ENVIRONMENT_SANDBOX
        val config = AdjustConfig(this, appToken, environment)
        config.setLogLevel(LogLevel.VERBOSE)
        Adjust.onCreate(config)

        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())
    }

    private class AdjustLifecycleCallbacks : Application.ActivityLifecycleCallbacks{
        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

        override fun onActivityCreated(p0: Activity, p1: Bundle?) {}

        override fun onActivityStarted(p0: Activity) {}

        override fun onActivityStopped(p0: Activity) {}

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

        override fun onActivityDestroyed(p0: Activity) {}

    }
}