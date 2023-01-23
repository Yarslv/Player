package com.yprodan.player.arch.ext

import android.media.MediaMetadataRetriever
import android.net.Uri
import java.io.File

fun Int.toTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    if (hours == 0) {
        return String.format("%02d:%02d", minutes, seconds)
    }
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

fun Uri.extractSeconds(): Int {
    if (this != Uri.EMPTY) {
        val m = MediaMetadataRetriever()
        m.setDataSource(File(this.path.toString()).path)
        val milliseconds = m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toInt()
        milliseconds?.let { return it / 1000 }
    }
    return 0
}
