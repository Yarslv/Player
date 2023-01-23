package com.yprodan.player.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.yprodan.player.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerService : Service(), MediaPlayer.OnPreparedListener {

    private lateinit var mMediaPlayer: MediaPlayer

    override fun onCreate() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.isLooping = false
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action.toString()) {
            Constant.ACTION_PLAY_TAG -> {
                mMediaPlayer.stop()
                mMediaPlayer.reset()
                mMediaPlayer.setDataSource(intent.getStringExtra("uri"))
                mMediaPlayer.apply {
                    setOnPreparedListener(this@PlayerService)
                }
                mMediaPlayer.prepare()
            }
            Constant.ACTION_PAUSE_TAG -> {
                if (mMediaPlayer.isPlaying) {
                    mMediaPlayer.pause()
                }
            }
            Constant.ACTION_RESUME_TAG -> {
                onPrepared(mMediaPlayer)
            }
            Constant.ACTION_SEEK_TAG -> {
                mMediaPlayer.seekTo(intent.getIntExtra(Constant.EXTRA_SEEK_TO_TAG, mMediaPlayer.currentPosition))
            }
        }
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()

        mMediaPlayer.setOnCompletionListener {
            sendBroadcast(Intent().apply { action = Constant.ACTION_NEXT_TAG })
        }

        CoroutineScope(Dispatchers.IO).launch {
            while (mMediaPlayer.isPlaying) {
                val intent =
                    Intent().apply {
                        putExtra(Constant.EXTRA_TIME_TAG, mMediaPlayer.currentPosition)
                        action = Constant.ACTION_TIME_TAG
                    }
                sendBroadcast(intent)
                delay(1000)
            }
        }
    }
}
