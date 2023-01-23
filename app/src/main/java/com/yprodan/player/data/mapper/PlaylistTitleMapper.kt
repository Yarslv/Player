package com.yprodan.player.data.mapper

import com.yprodan.player.arch.mapper.Mapper
import com.yprodan.player.network.entity.playlist_title.PlaylistTitleEntity

class PlaylistTitleMapper : Mapper<PlaylistTitleEntity, String> {
    override fun toDomain(model: PlaylistTitleEntity): String {
        val snippet = (model.items ?: throw(Exception()))[0].snippet
        val title = (snippet ?: throw(Exception())).title
        return title ?: throw(Exception())
    }
}