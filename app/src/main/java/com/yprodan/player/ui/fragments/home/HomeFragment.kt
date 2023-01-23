package com.yprodan.player.ui.fragments.home


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.yprodan.player.Constant
import com.yprodan.player.R
import com.yprodan.player.arch.BaseFragment
import com.yprodan.player.databinding.FragmentHomeBinding
import com.yprodan.player.service.PlayerService
import com.yprodan.player.ui.fragments.home.adapter.HomePageAdapter
import com.yprodan.player.ui.fragments.home.player.PlayerState
import com.yprodan.player.ui.fragments.home.player.PlayerViewModel
import com.yprodan.player.ui.fragments.home.player.SlidePanelState
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeFragmentViewModel by viewModel()
    val playerViewModel: PlayerViewModel by viewModel()
    private var youtubePlayer: YouTubePlayer? = null

    override fun setObservers() {
        registerBroadcast()
        binding.viewModel = viewModel
        binding.playerViewModel = playerViewModel
        lifecycle.addObserver(binding.playerNormal.youtubePlayer)
        binding.playerNormal.youtubePlayer.enableAutomaticInitialization = false

        binding.playerNormal.youtubePlayer.initialize(
            getYoutubePlayerListener(),
            IFramePlayerOptions.Builder().controls(0).ivLoadPolicy(0).ccLoadPolicy(0).rel(0).build()
        )

        createPageAdapter()

        val intent = Intent(requireContext(), PlayerService::class.java)

        binding.playerNormal.seekBar.setOnSeekBarChangeListener(getSeekBarListener(intent))
        binding.miniPlayer.seekBar.setOnSeekBarChangeListener(getSeekBarListener(intent))

        binding.slidingLayout.addPanelSlideListener(getPanelStateListener())

        playerViewModel.playerState.observe(viewLifecycleOwner) {
            youtubePlayer?.let { player ->
                when (it) {
                    PlayerState.PlayingYoutube -> {
                        player.play()
                    }
                    PlayerState.Stopping, PlayerState.PausedMP3, PlayerState.PlayingMP3, PlayerState.PausingYoutube -> {
                        player.pause()
                    }
                    else -> {}
                }
            }
        }
        playerViewModel.videoModel.observe(viewLifecycleOwner) {
            youtubePlayer?.loadVideo(it.videoId, 0f)
        }
    }

    private fun registerBroadcast() {
        val rec = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent) {

                when (p1.action) {
                    Constant.ACTION_NOTIFICATION_PREVIOUS_TAG -> playerViewModel.previous()
                    Constant.ACTION_NOTIFICATION_PLAY_PAUSE_TAG -> playerViewModel.playPause()
                    Constant.ACTION_NOTIFICATION_NEXT_TAG -> playerViewModel.next()
                    Constant.ACTION_NOTIFICATION_STOP_TAG -> playerViewModel.stop()
                    Constant.ACTION_NEXT_TAG -> playerViewModel.next()
                    Constant.ACTION_TIME_TAG -> {
                        val time = p1.getIntExtra(Constant.EXTRA_TIME_TAG, -1) / 1000
                        if (!playerViewModel.isSeek) {
                            playerViewModel.setCurrentTime(time)
                            playerViewModel.setTime(time)
                        }
                    }
                }
            }
        }
        val filter = IntentFilter().apply {
            addAction(Constant.ACTION_TIME_TAG)
            addAction(Constant.ACTION_NEXT_TAG)
            addAction(Constant.ACTION_NOTIFICATION_PREVIOUS_TAG)
            addAction(Constant.ACTION_NOTIFICATION_PLAY_PAUSE_TAG)
            addAction(Constant.ACTION_NOTIFICATION_NEXT_TAG)
            addAction(Constant.ACTION_NOTIFICATION_STOP_TAG)
        }
        requireActivity().registerReceiver(rec, filter)
    }

    override fun onDestroy() {
        requireActivity().stopService(Intent(requireContext(), PlayerService::class.java))
        super.onDestroy()
    }

    private fun createPageAdapter() {
        val pageAdapter = HomePageAdapter(requireActivity())
        binding.homePager.apply {
            isUserInputEnabled = false
            adapter = pageAdapter
        }
        binding.homeBottomNavView.setOnItemSelectedListener {
            val index = when (it.itemId) {
                R.id.musicTabItem -> 0
                R.id.filesTabItem -> 1
                else -> return@setOnItemSelectedListener false
            }
            binding.homePager.setCurrentItem(index, true)
            return@setOnItemSelectedListener true
        }
    }

    private fun getPanelStateListener() =
        object : PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {}

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING && newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    if (!playerViewModel.isStoping) playerViewModel._panelState.postValue(
                        SlidePanelState.Collapsed
                    )
                }
            }
        }

    private fun getYoutubePlayerListener(): AbstractYouTubePlayerListener {
        return object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@HomeFragment.youtubePlayer = youTubePlayer
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                if (!playerViewModel.isSeek) {
                    playerViewModel.setTime(second.toInt())
                    playerViewModel.setCurrentTime(second.toInt())
                }
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.ENDED) {
                    playerViewModel.next()
                }
                super.onStateChange(youTubePlayer, state)
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                playerViewModel.setTotalTime(duration)
            }
        }
    }

    private fun getSeekBarListener(intent: Intent): OnSeekBarChangeListener {
        return object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

            override fun onStartTrackingTouch(p0: SeekBar?) {
                playerViewModel.isSeek = true
            }

            override fun onStopTrackingTouch(p0: SeekBar) {
                when (playerViewModel.playerState.value) {
                    PlayerState.PlayingYoutube, PlayerState.PausingYoutube -> {
                        youtubePlayer?.seekTo(p0.progress.toFloat())
                    }
                    PlayerState.PlayingMP3, PlayerState.PausedMP3 -> {
                        intent.apply {
                            putExtra(Constant.EXTRA_SEEK_TO_TAG, p0.progress * 1000)
                        }
                        intent.action = Constant.ACTION_SEEK_TAG
                        requireActivity().startService(intent)
                    }
                    else -> {}
                }
                playerViewModel.isSeek = false
            }
        }
    }
}
