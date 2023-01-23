package com.yprodan.player

import android.app.Application
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
    }
}