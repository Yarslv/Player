package com.yprodan.player.ui.fragments.local_music

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.yprodan.player.Constant
import com.yprodan.player.R
import com.yprodan.player.arch.BaseFragment
import com.yprodan.player.arch.ext.makeToast
import com.yprodan.player.arch.ext.showNotifications
import com.yprodan.player.databinding.FragmentLocalMusicBinding
import com.yprodan.player.service.PlayerService
import com.yprodan.player.ui.fragments.home.player.ControlClick
import com.yprodan.player.ui.fragments.home.player.PlayerState
import com.yprodan.player.ui.fragments.home.player.PlayerViewModel
import com.yprodan.player.ui.fragments.local_music.adapter.MusicRecyclerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocalMusicFragment : BaseFragment<FragmentLocalMusicBinding>(R.layout.fragment_local_music) {

    override val viewModel: LocalMusicFragmentViewModel by viewModel()
    val playerViewModel: PlayerViewModel by viewModel()
    private lateinit var activityResultLauncher: ActivityResultLauncher<String>


    override fun setObservers() {
        checkPermission { onPermissionGranted() }
        activityResultLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        binding.viewModel = viewModel

    }

    private fun checkPermission(callback: () -> Unit) {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                callback()
            } else {
                viewModel.setError(true)
                makeToast(R.string.permission_error_text)
            }
        }
    }

    private fun onPermissionGranted() {
        val serviceIntent = Intent(requireContext(), PlayerService::class.java)

        val adapter = MusicRecyclerAdapter(arrayListOf())
        {
            serviceIntent.apply {
                putExtra(Constant.EXTRA_URI_TAG, it.uri.path)
                action = Constant.ACTION_PLAY_TAG
            }
            requireActivity().startService(serviceIntent)
            playerViewModel.setMP3Content()
            playerViewModel.showPlayerM()
            playerViewModel.setImageUri(it.uri)
            playerViewModel.setName(it.name)
            playerViewModel.setArtist(it.artist)

        }.apply {
            viewModel.musicList.observe(viewLifecycleOwner) {
                setContent(it)
            }
        }
        binding.recycler.adapter = adapter
        playerViewModel.controlClick.observe(viewLifecycleOwner) { click ->
            if (playerViewModel.playerState.value == PlayerState.PlayingMP3 || playerViewModel.playerState.value == PlayerState.PausedMP3)
                when (click) {
                    ControlClick.Next -> adapter.getNext()
                    ControlClick.Previous -> adapter.getPrevious()
                    else -> {}
                }

        }

        playerViewModel.playerState.observe(viewLifecycleOwner) {
            when (it) {
                PlayerState.PlayingMP3 -> {
                    serviceIntent.action = Constant.ACTION_RESUME_TAG
                    val uri = adapter.getCurrent().uri
                    val title = adapter.getCurrent().artist
                    showNotifications(uri, title, true)
                    requireActivity().startService(serviceIntent)
                }
                PlayerState.PausedMP3 -> {
                    serviceIntent.action = Constant.ACTION_PAUSE_TAG
                    val uri = adapter.getCurrent().uri
                    val title = adapter.getCurrent().artist
                    showNotifications(uri, title, false)
                    requireActivity().startService(serviceIntent)
                }
                PlayerState.PlayingYoutube, PlayerState.PausingYoutube, PlayerState.Stopping -> {
                    serviceIntent.action = Constant.ACTION_PAUSE_TAG
                    requireActivity().startService(serviceIntent)
                }
                else -> {}
            }
        }
        viewModel.onRefresh()
    }
}