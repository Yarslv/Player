package com.yprodan.player.data.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.yprodan.player.domain.entity.MusicModel

interface LocalMusicRepository {
    fun getAllMusic(): List<MusicModel>
    fun getSortedMusic(text: String): List<MusicModel>
}

class LocalMusicRepositoryImpl(private val context: Context) : LocalMusicRepository {
    override fun getAllMusic(): List<MusicModel> {
        val outputList = arrayListOf<MusicModel>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val contentResolver = context.contentResolver.query(uri, projection, null, null, null)

        if (contentResolver != null) {
            while (contentResolver.moveToNext()) {

                val uriString =                    contentResolver.getString(0)
                outputList.add(
                    MusicModel(
                        contentResolver.getString(1),
                        contentResolver.getString(2),
                        Uri.parse(uriString)
                    )
                )
            }
        }
        return outputList
    }

    override fun getSortedMusic(text: String): List<MusicModel> {
        val allMusic = getAllMusic()
        return allMusic.filter { it.name.contains(text, true) }
    }
}