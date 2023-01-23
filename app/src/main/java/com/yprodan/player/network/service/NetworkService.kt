package com.yprodan.player.network.service

import com.yprodan.player.network.entity.playlist_title.PlaylistTitleEntity
import com.yprodan.player.network.entity.playlist_videos.VideosListEntity
import com.yprodan.player.network.entity.search.SearchEntity
import com.yprodan.player.network.response.Either

class NetworkService(
    private val service: Api,
    private val apiAnswer: ApiAnswerService
) {

    suspend fun getPlaylistTitleById(id: String): Either<PlaylistTitleEntity> {
        return try {
            val response = service.getPlaylistTitleById(id)
            return apiAnswer.extractAnswer(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getVideosListById(id: String): Either<VideosListEntity> {
        return try {
            val response = service.getVideosListById(id)
            return apiAnswer.extractAnswer(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getSearchResult(word: String): Either<SearchEntity> {
        return try {
            val response = service.searchByKeywords(word)
            return apiAnswer.extractAnswer(response)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}