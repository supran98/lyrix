package com.example.lyrix.repository

import androidx.lifecycle.LiveData
import com.example.lyrix.model.Track
import com.example.lyrix.room.TrackDao

class RoomRepository(private val trackDao: TrackDao) {
    val recentTracks: LiveData<List<Track>> = trackDao.getRecentTracks()

    suspend fun addTrack(track: Track) {
        trackDao.addTrack(track)
    }
}