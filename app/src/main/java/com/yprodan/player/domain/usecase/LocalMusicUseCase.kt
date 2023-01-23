package com.yprodan.player.domain.usecase

import com.yprodan.player.data.repository.LocalMusicRepository
import com.yprodan.player.domain.entity.MusicModel

class LocalMusicUseCase(private val repository: LocalMusicRepository) {
    fun getLocalMusic(text: String = ""): List<MusicModel>{

        return when(text.length){
            0 -> repository.getAllMusic()
            else -> repository.getSortedMusic(text)
        }
    }
}