package com.yprodan.player.di

import com.yprodan.player.network.service.ApiAnswerService
import com.yprodan.player.Constant
import com.yprodan.player.network.api.NetworkProvider
import com.yprodan.player.network.service.Api
import com.yprodan.player.network.service.NetworkService
import org.koin.dsl.module

val networkModule = module {
    single { NetworkProvider.provideGson() }
    single {
        NetworkProvider.provideApiService(
            get(), Constant.BASE_URL, Api::class.java
        )
    }
    single { NetworkService(get(), get()) }
    single { ApiAnswerService() }
}