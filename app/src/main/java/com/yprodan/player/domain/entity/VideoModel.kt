package com.yprodan.player.domain.entity

import com.yprodan.player.arch.adapter.AdapterContentElement

data class VideoModel(
    val title: String = "",
    val channelTitle: String = "",
    val imageUrl: String = "",
    val videoId: String = ""
):AdapterContentElement {
    override fun areContentsTheSame(other: AdapterContentElement): Boolean {
        return this.videoId == (other as VideoModel).videoId
    }
}