package com.yprodan.player.di

import com.yprodan.player.domain.usecase.LocalMusicUseCase
import com.yprodan.player.domain.usecase.YouTubeUseCase
import com.yprodan.player.service.PlayerService
import org.koin.dsl.module

val providerModule = module {
    single { LocalMusicUseCase(get()) }
    single { PlayerService() }
    single { YouTubeUseCase(get()) }

}