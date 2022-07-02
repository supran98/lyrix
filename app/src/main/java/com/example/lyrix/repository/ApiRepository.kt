package com.example.lyrix.repository

import android.content.Context
import com.example.lyrix.api.RetrofitInstance
import com.example.lyrix.model.TrackData
import com.example.lyrix.model.TrackWrapper
import com.example.lyrix.utils.RawReader
import kotlinx.coroutines.*
import retrofit2.Response

class ApiRepository(context: Context) {
    private val apiKey: String // = "313b3caefb6b0831400efe3376115edf"
    private val minPageSize: Int

    init {
        val rawReader = RawReader(context)
        apiKey = rawReader.getProperty("apiKey")
        minPageSize = rawReader.getProperty("minPageSize")
    }

    suspend fun getSortedTracks(query: String): List<TrackWrapper> = coroutineScope {
        val deferredResponses = mutableListOf<Deferred<List<TrackWrapper>>>()
        val splitQuery = query.split(" ")

        deferredResponses.apply {
            add(getTracks("", query))
            if (splitQuery.size != 1) {
                add(getTracks(splitQuery.first(), splitQuery.last()))
                add(getTracks(splitQuery.last(), splitQuery.first()))
            }
            add(getTracks(query, ""))
        }

        deferredResponses.awaitAll().flatten()
    }

    private suspend fun getTracks(artistName: String, trackName: String): Deferred<List<TrackWrapper>> = coroutineScope {
        async {
            val response = RetrofitInstance.api.getTracks(apiKey, artistName, trackName, minPageSize)
            if (response.isSuccessful) response.body()!!.message.body.track_list else emptyList()
        }
    }

    suspend fun getLyrics(commontrackId: Int): Response<TrackData> {
        return RetrofitInstance.api.getLyrics(apiKey, commontrackId)
    }
}