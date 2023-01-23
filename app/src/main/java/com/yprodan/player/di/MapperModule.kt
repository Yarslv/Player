package com.yprodan.player.di

import com.yprodan.player.data.mapper.PlaylistTitleMapper
import com.yprodan.player.data.mapper.PlaylistVideosMapper
import com.yprodan.player.data.mapper.SearchResultVideosMapper
import org.koin.dsl.module

val mapperModule = module {
    single { SearchResultVideosMapper() }
    single { PlaylistVideosMapper() }
    single { PlaylistTitleMapper() }
}