package com.yprodan.player.ui.fragments.local_music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yprodan.player.arch.BaseViewModel
import com.yprodan.player.domain.entity.MusicModel
import com.yprodan.player.domain.usecase.LocalMusicUseCase

class LocalMusicFragmentViewModel(
    private val useCase: LocalMusicUseCase
) : BaseViewModel() {

    private val _isError = MutableLiveData<Boolean>(false)
    var isError: LiveData<Boolean> = _isError

    private val _musicList = MutableLiveData<List<MusicModel>>()
    val musicList: LiveData<List<MusicModel>> = _musicList

    init {
        getAllMusic()
    }

    private fun getAllMusic() {
        try {
            _musicList.postValue(useCase.getLocalMusic())
            _isError.postValue(false)
        } catch (e: Throwable) {
            _isError.postValue(true)
        }
    }

    fun getByText(text: String): Boolean {
        try {
            _musicList.postValue(useCase.getLocalMusic(text))
            _isError.postValue(false)
        } catch (e: Throwable) {
            _isError.postValue(true)
        }
        return true
    }

    fun onRefresh(){
        getAllMusic()
    }
}