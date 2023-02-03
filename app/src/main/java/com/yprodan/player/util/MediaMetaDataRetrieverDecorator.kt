package com.yprodan.player.util

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import java.io.File
import java.io.FileInputStream

class MediaMetaDataRetrieverDecorator(context: Context) {
    private val m = MediaMetadataRetriever()
    private var isError = false

    private val callbackList = arrayListOf<(Uri) -> Unit>({
        m.setDataSource(it.path)
    }, {
        m.setDataSource(context, it)
    }, {
        m.setDataSource(it.path, HashMap())
    }, {
        m.setDataSource(File(it.path.toString()).path)
    }, {
        m.setDataSource(FileInputStream(File(it.path.toString()).absolutePath).fd)
    })

    fun setDataSource(uri: Uri) {
        callbackList.forEach {
            try {
                it.invoke(uri)
                isError = false
                return@forEach
            } catch (e: Throwable) {
                isError = true
            }
        }
    }

    fun getEmbeddedPictureOrEmpty() =
        if (isError || m.embeddedPicture == null) byteArrayOf() else m.embeddedPicture!!

}
