package com.yprodan.player.network.entity.playlist_videos

data class Snippet(
    val videoOwnerChannelTitle: String,
    val resourceId: ResourceId,
    val thumbnails: Thumbnails,
    val title: String
)