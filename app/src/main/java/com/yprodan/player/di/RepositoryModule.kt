package com.yprodan.player.di

import com.yprodan.player.data.repository.LocalMusicRepository
import com.yprodan.player.data.repository.LocalMusicRepositoryImpl
import com.yprodan.player.data.repository.YoutubeRepository
import com.yprodan.player.data.repository.YoutubeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<LocalMusicRepository> { LocalMusicRepositoryImpl(get()) }
    single<YoutubeRepository> {YoutubeRepositoryImpl(get(), get(), get(), get())}
}