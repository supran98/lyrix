package com.example.lyrix.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lyrix.model.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrack(track: Track)

    @Query("SELECT * FROM track_table ORDER BY id DESC LIMIT 15")
    fun getRecentTracks(): LiveData<List<Track>>
}