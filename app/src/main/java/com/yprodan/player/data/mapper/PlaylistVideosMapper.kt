package com.yprodan.player.data.mapper

import com.yprodan.player.arch.mapper.Mapper
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.network.entity.playlist_videos.VideosListEntity

class PlaylistVideosMapper : Mapper<VideosListEntity, List<VideoModel>> {
    override fun toDomain(model: VideosListEntity): List<VideoModel> {
        val output = arrayListOf<VideoModel>()
        val list = model.items ?: throw Exception("Empty list")
        list.forEach {
            output.add(
                VideoModel(
                    title = it.snippet.title,
                    channelTitle = it.snippet.videoOwnerChannelTitle,
                    imageUrl = it.snippet.thumbnails.medium.url,
                    videoId = it.snippet.resourceId.videoId
                )
            )
        }
        return output
    }
}