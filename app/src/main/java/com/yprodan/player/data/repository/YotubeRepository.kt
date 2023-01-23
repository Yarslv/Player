package com.yprodan.player.data.repository

import com.yprodan.player.data.mapper.PlaylistTitleMapper
import com.yprodan.player.data.mapper.PlaylistVideosMapper
import com.yprodan.player.data.mapper.SearchResultVideosMapper
import com.yprodan.player.domain.entity.VideoModel
import com.yprodan.player.network.response.Either
import com.yprodan.player.network.response.map
import com.yprodan.player.network.service.NetworkService

interface YoutubeRepository {
    suspend fun getPlaylistTitleById(id: String): Either<String>
    suspend fun getVideos(id: String): Either<List<VideoModel>>
    suspend fun getSearchResultVideos(word: String): Either<List<VideoModel>>
}

class YoutubeRepositoryImpl(
    private val networkService: NetworkService,
    private val playlistTitleMapper: PlaylistTitleMapper,
    private val playlistVideosMapper: PlaylistVideosMapper,
    private val searchResultVideosMapper: SearchResultVideosMapper
) : YoutubeRepository {
    override suspend fun getPlaylistTitleById(id: String): Either<String> {
        return try {
            networkService.getPlaylistTitleById(id).map { playlistTitleMapper.toDomain(it) }
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    override suspend fun getVideos(id: String): Either<List<VideoModel>> {
        return try {
            networkService.getVideosListById(id).map { playlistVideosMapper.toDomain(it) }
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

    override suspend fun getSearchResultVideos(word: String): Either<List<VideoModel>> {
        return try {
            networkService.getSearchResult(word).map { searchResultVideosMapper.toDomain(it) }
        } catch (e: Throwable) {
            Either.failure(e)
        }
    }

}