package com.yprodan.player.ui.fragments.youtube

import com.yprodan.player.R
import com.yprodan.player.arch.BaseFragment
import com.yprodan.player.arch.ext.showNotifications
import com.yprodan.player.databinding.FragmentYoutubeBinding
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.ui.fragments.home.player.ControlClick
import com.yprodan.player.ui.fragments.home.player.PlayerState
import com.yprodan.player.ui.fragments.home.player.PlayerViewModel
import com.yprodan.player.ui.fragments.home.player.VideoList
import com.yprodan.player.ui.fragments.youtube.adapter.SearchedVideoListAdapter
import com.yprodan.player.ui.fragments.youtube.adapter.HorizontalVideoListAdapter
import com.yprodan.player.ui.fragments.youtube.adapter.VerticalVideoListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class YoutubeFragment : BaseFragment<FragmentYoutubeBinding>(R.layout.fragment_youtube) {
    override val viewModel: YoutubeFragmentViewModel by viewModel()
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun setObservers() {
        binding.viewModel = viewModel

        val hor = HorizontalVideoListAdapter(arrayListOf()) { initCallback(it, VideoList.First) }
        binding.horizontalRecycler.adapter = hor
        viewModel.firstList.observe(viewLifecycleOwner) { hor.setContent(it) }

        val ver = VerticalVideoListAdapter(arrayListOf()) { initCallback(it, VideoList.Second) }
        binding.verticalRecycler.adapter = ver
        viewModel.secondList.observe(viewLifecycleOwner) { ver.setContent(it) }

        val ser = SearchedVideoListAdapter(arrayListOf()) { initCallback(it, VideoList.Search) }
        binding.searchRecycler.adapter = ser
        viewModel.searchList.observe(viewLifecycleOwner) { ser.setContent(it) }

        playerViewModel.controlClick.observe(viewLifecycleOwner) {
            val clickFrom = when (playerViewModel.currentVideoList.value) {
                VideoList.First -> hor
                VideoList.Second -> ver
                VideoList.Search -> ser
                null -> null
            }
            clickFrom?.let { adapter ->
                val current = adapter.getCurrent()
                if (playerViewModel.playerState.value == PlayerState.PlayingYoutube || playerViewModel.playerState.value == PlayerState.PausingYoutube)
                    when (it) {
                        ControlClick.Next -> {
                            adapter.getNext()

                        }
                        ControlClick.Previous -> {
                            adapter.getPrevious()
                        }
                        ControlClick.Pause -> {
                            showNotifications(current.imageUrl, current.title, false)

                        }
                        ControlClick.Play -> {
                            showNotifications(current.imageUrl, current.title, true)

                        }
                        null -> {}
                    }
            }
        }


        binding.searchView.setOnCloseListener {
            viewModel.toViewList()
            false
        }
    }

    private fun initCallback(model: VideoModel, type: VideoList) {
        playerViewModel.setName(model.title)
        showNotifications(model.imageUrl, model.title, true)
        playerViewModel.setArtist(model.channelTitle)
        playerViewModel._videoModel.postValue(model)
        playerViewModel.setYoutubeContent()
        playerViewModel._currentVideoList.postValue(type)
        playerViewModel.showPlayerY()
    }
}