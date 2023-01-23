package com.yprodan.player.domain.entity

import android.net.Uri
import com.yprodan.player.arch.adapter.AdapterContentElement

data class MusicModel(
    val name: String, val artist: String, val uri: Uri
) : AdapterContentElement {
    override fun areContentsTheSame(other: AdapterContentElement): Boolean {
        return this.uri == (other as MusicModel).uri
    }
}
