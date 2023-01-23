package com.yprodan.player.ui.fragments.youtube

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yprodan.player.Constant
import com.yprodan.player.arch.BaseViewModel
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.domain.usecase.YouTubeUseCase
import com.yprodan.player.network.response.onFailure
import com.yprodan.player.network.response.onSuccess
import kotlinx.coroutines.launch

class YoutubeFragmentViewModel(private val useCase: YouTubeUseCase) : BaseViewModel() {

    private val _isHorizontalListVisible = MutableLiveData<Boolean>(true)
    var isHorizontalListVisible: LiveData<Boolean> = _isHorizontalListVisible

    private val _isVerticalListVisible = MutableLiveData<Boolean>(true)
    var isVerticalListVisible: LiveData<Boolean> = _isVerticalListVisible

    private val _isError = MutableLiveData<Boolean>(false)
    var isError: LiveData<Boolean> = _isError

    private val _state = MutableLiveData(YoutubeFragmentState.ViewLists)
    val state: LiveData<YoutubeFragmentState> = _state

    private val _firstTitle = MutableLiveData("")
    val firstTitle: LiveData<String> = _firstTitle

    private val _secondTitle = MutableLiveData("")
    val secondTitle: LiveData<String> = _secondTitle

    private val _firstList = MutableLiveData<List<VideoModel>>()
    val firstList: LiveData<List<VideoModel>> = _firstList

    private val _secondList = MutableLiveData<List<VideoModel>>()
    val secondList: LiveData<List<VideoModel>> = _secondList

    private val _searchList = MutableLiveData<List<VideoModel>>()
    val searchList: LiveData<List<VideoModel>> = _searchList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            useCase.getPlaylistTitle(Constant.playlistIdFirst)
                .onSuccess {
                    _firstTitle.postValue(it)
                    _isError.postValue(false)
                }
                .onFailure { _isError.postValue(true) }
            useCase.getPlaylistVideos(Constant.playlistIdFirst)
                .onSuccess {
                    _isHorizontalListVisible.postValue(it.isNotEmpty())
                    _firstList.postValue(it)
                    _isError.postValue(false)
                }
                .onFailure { _isError.postValue(true) }
            useCase.getPlaylistTitle(Constant.playlistIdSecond)
                .onSuccess {
                    _isVerticalListVisible.postValue(it.isNotEmpty())
                    _secondTitle.postValue(it)
                    _isError.postValue(false)
                }
                .onFailure { _isError.postValue(true) }
            useCase.getPlaylistVideos(Constant.playlistIdSecond)
                .onSuccess {
                    _secondList.postValue(it)
                    _isError.postValue(false)
                }
                .onFailure { _isError.postValue(true) }
        }

    }

    fun onSearch(text: String): Boolean {
        if (text.isEmpty()) {
            toViewList()
        } else {
            viewModelScope.launch {
                toSearch()
                useCase.getSearchResult(text)
                    .onSuccess {
                        _searchList.postValue(it)
                        _isError.postValue(false)
                    }
                    .onFailure { _isError.postValue(true) }
            }
        }
        return true
    }

    private fun toSearch() {
        _state.postValue(YoutubeFragmentState.Search)
    }

    fun toViewList() {
        _state.postValue(YoutubeFragmentState.ViewLists)
    }

    fun onRefresh() {
        loadData()
    }

}

enum class YoutubeFragmentState {
    ViewLists, Search
}