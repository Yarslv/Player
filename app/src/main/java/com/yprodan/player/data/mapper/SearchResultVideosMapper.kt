package com.yprodan.player.data.mapper

import com.yprodan.player.arch.mapper.Mapper
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.network.entity.search.SearchEntity

class SearchResultVideosMapper : Mapper<SearchEntity, List<VideoModel>> {
    override fun toDomain(model: SearchEntity): List<VideoModel> {

        val output = arrayListOf<VideoModel>()
        val list = model.items ?: throw Exception("Empty list")
        list.forEach {
            output.add(
                VideoModel(
                    title = it.snippet.title,
                    channelTitle = it.snippet.channelTitle,
                    imageUrl = it.snippet.thumbnails.medium.url,
                    videoId = it.id.videoId
                )
            )
        }
        return output
    }
}