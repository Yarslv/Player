package com.yprodan.player.ui.fragments.home.player

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yprodan.player.arch.BaseViewModel
import com.yprodan.player.arch.ext.extractSeconds
import com.yprodan.player.arch.ext.toTime
import com.yprodan.player.domain.entity.VideoModel

class PlayerViewModel : BaseViewModel() {

    var isSeek = false

    val _currentVideoList = MutableLiveData<VideoList>()
    val currentVideoList: LiveData<VideoList> = _currentVideoList

    val _panelState = MutableLiveData(SlidePanelState.Hided)
    val panelState: LiveData<SlidePanelState> = _panelState

    val _seekBarTime = MutableLiveData(0)
    val seekBarTime: LiveData<Int> = _seekBarTime

    val _imageUri = MutableLiveData(Uri.EMPTY)
    val imageUri: LiveData<Uri> = _imageUri

    val _compositionName = MutableLiveData("")
    val compositionName: LiveData<String> = _compositionName

    val _compositionArtist = MutableLiveData("")
    val compositionArtist: LiveData<String> = _compositionArtist


    val _currentTime = MutableLiveData("00:00")
    val currentTime: LiveData<String> = _currentTime

    val _totalTime = MutableLiveData(0)
    val totalTime: LiveData<Int> = _totalTime

    val _videoModel = MutableLiveData<VideoModel>()
    val videoModel: LiveData<VideoModel> = _videoModel


    val _contentType = MutableLiveData(ContentType.None)
    val contentType: LiveData<ContentType> = _contentType

    val _controlClick = MutableLiveData<ControlClick>()
    val controlClick: LiveData<ControlClick> = _controlClick

    val _playerState = MutableLiveData(PlayerState.NoMusic)
    val playerState: LiveData<PlayerState> = _playerState


    fun setYoutubeContent() {
        _playerState.postValue(PlayerState.PausedMP3)
        _contentType.postValue(ContentType.YouTube)
    }

    fun setMP3Content() {
        _playerState.postValue(PlayerState.PausingYoutube)
        _contentType.postValue(ContentType.MP3)
    }


    fun showPlayerY() {
        isStoping = false
        if (panelState.value == SlidePanelState.Hided)
            _panelState.postValue(SlidePanelState.Collapsed)
        _playerState.postValue(PlayerState.PlayingYoutube)
    }

    fun showPlayerM() {
        isStoping = false
        if (panelState.value == SlidePanelState.Hided)
            _panelState.postValue(SlidePanelState.Collapsed)
        _playerState.postValue(PlayerState.PlayingMP3)
    }

    fun next() {
        _controlClick.postValue(ControlClick.Next)
        when (playerState.value) {
            PlayerState.PlayingYoutube -> _playerState.postValue(PlayerState.PlayingYoutube)
            PlayerState.PausingYoutube -> _playerState.postValue(PlayerState.PlayingYoutube)
            PlayerState.PlayingMP3 -> _playerState.postValue(PlayerState.PlayingMP3)
            PlayerState.PausedMP3 -> _playerState.postValue(PlayerState.PlayingMP3)
            else -> {}
        }
    }

    fun previous() {
        _controlClick.postValue(ControlClick.Previous)
        when (playerState.value) {
            PlayerState.PlayingYoutube -> _playerState.postValue(PlayerState.PlayingYoutube)
            PlayerState.PausingYoutube -> _playerState.postValue(PlayerState.PlayingYoutube)
            PlayerState.PlayingMP3 -> _playerState.postValue(PlayerState.PlayingMP3)
            PlayerState.PausedMP3 -> _playerState.postValue(PlayerState.PlayingMP3)
            else -> {}
        }
    }

    fun playPause() {

        when (playerState.value) {
            PlayerState.PlayingYoutube -> {
                _controlClick.postValue(ControlClick.Pause)
                _playerState.postValue(PlayerState.PausingYoutube)
            }
            PlayerState.PlayingMP3 -> {
//                _controlClick.postValue(ControlClick.Play)
                _playerState.postValue(PlayerState.PausedMP3)
            }
            PlayerState.PausingYoutube -> {
                _controlClick.postValue(ControlClick.Play)
                _playerState.postValue(PlayerState.PlayingYoutube)
            }
            PlayerState.PausedMP3 -> {
//                _controlClick.postValue(ControlClick.Play)
                _playerState.postValue(PlayerState.PlayingMP3)
            }
            else -> {}
        }
    }

    var isStoping = false

    fun stop() {
        isStoping = true
        _playerState.postValue(PlayerState.Stopping)
        _panelState.postValue(SlidePanelState.Hided)
    }

    fun setTotalTime(uri: Uri) {
        _totalTime.postValue(uri.extractSeconds())
    }

    fun setTotalTime(time: Float) {
        _totalTime.postValue(time.toInt())
    }

    fun setCurrentTime(time: Int) {
        if (!isSeek)
            _currentTime.postValue(time.toTime())
    }

    fun setTime(time: Int) {
        _seekBarTime.postValue(time)
    }

    fun setImageUri(uri: Uri) {
        setTotalTime(uri)
        _imageUri.postValue(uri)
    }

    fun setName(name: String) {
        _compositionName.postValue(name)
    }

    fun setArtist(artist: String) {
        _compositionArtist.postValue(artist)
    }

    fun collapsePlayer() {
        _panelState.postValue(SlidePanelState.Collapsed)
    }

    fun expandPlayer() {
        _panelState.postValue(SlidePanelState.Expanded)
    }
}

enum class SlidePanelState {
    Hided, Collapsed, Expanded
}

enum class ControlClick {
    Next, Previous, Pause, Play
}

enum class PlayerState {
    PlayingYoutube, PausingYoutube, PlayingMP3, PausedMP3, NoMusic, Stopping
}

enum class ContentType {
    YouTube, MP3, None
}

enum class VideoList {
    First, Second, Search
}