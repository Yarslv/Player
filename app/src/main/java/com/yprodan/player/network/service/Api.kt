package com.yprodan.player.network.service

import com.yprodan.player.Constant
import com.yprodan.player.network.entity.playlist_title.PlaylistTitleEntity
import com.yprodan.player.network.entity.playlist_videos.VideosListEntity
import com.yprodan.player.network.entity.search.SearchEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("playlists?part=snippet&fields=items(snippet(title))&key=${Constant.API_KEY}")
    suspend fun getPlaylistTitleById(@Query("id") id: String): Response<PlaylistTitleEntity>

    @GET("playlistItems?&part=id,snippet&fields=items(id,snippet(resourceId(videoId),title,%20videoOwnerChannelTitle,%20thumbnails(medium(url))))&maxResults=20&key=${Constant.API_KEY}")
    suspend fun getVideosListById(@Query("playlistId") id: String): Response<VideosListEntity>

    @GET("search?part=snippet,id&fields=items(id(videoId),snippet(title,channelTitle,%20thumbnails(medium(url))))&maxResults=20&type=video&key=${Constant.API_KEY}")
    suspend fun searchByKeywords(@Query("q") word: String): Response<SearchEntity>

}