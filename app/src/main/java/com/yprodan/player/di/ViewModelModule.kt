package com.yprodan.player.di

import com.yprodan.player.ui.fragments.home.HomeFragmentViewModel
import com.yprodan.player.ui.fragments.home.player.PlayerViewModel
import com.yprodan.player.ui.fragments.local_music.LocalMusicFragmentViewModel
import com.yprodan.player.ui.fragments.youtube.YoutubeFragmentViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { LocalMusicFragmentViewModel(get()) }
    single { HomeFragmentViewModel() }
    single { PlayerViewModel() }
    single { YoutubeFragmentViewModel(get()) }
}