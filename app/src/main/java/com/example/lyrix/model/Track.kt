package com.example.lyrix.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "track_table")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val album_id: Int,
    val album_name: String,
    val artist_id: Int,
    val artist_name: String,
    val commontrack_id: Int,
    val track_id: Int,
    val track_name: String,
) : Parcelable