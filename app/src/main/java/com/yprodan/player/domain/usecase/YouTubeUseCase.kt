package com.yprodan.player.domain.usecase

import com.yprodan.player.data.repository.YoutubeRepository
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.network.response.Either

class YouTubeUseCase(private val repository: YoutubeRepository) {
    suspend fun getPlaylistTitle(id: String): Either<String> {
        return try {
            repository.getPlaylistTitleById(id)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getPlaylistVideos(id: String): Either<List<VideoModel>> {
        return try {
            repository.getVideos(id)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    suspend fun getSearchResult(word: String): Either<List<VideoModel>> {
        return try {
            repository.getSearchResultVideos(word)
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }
}