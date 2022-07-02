package com.example.lyrix.api

import com.example.lyrix.model.TrackData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusixmatchApi {
    @GET("track.search")
    suspend fun getTracks(
        @Query("apikey") apiKey: String,
        @Query("q_artist") artistName: String,
        @Query("q_track") trackName: String,
        @Query("page_size") pageSize: Int
    ): Response<TrackData>

    @GET("track.lyrics.get")
    suspend fun getLyrics(
        @Query("apikey") apiKey: String,
        @Query("commontrack_id") commontrackId: Int
    ): Response<TrackData>
}