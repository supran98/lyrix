package com.example.lyrix.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyrix.model.Track
import com.example.lyrix.repository.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    val recentTracks: LiveData<List<Track>> = repository.recentTracks

    fun addTrack(track: Track) {
        viewModelScope.launch {
            repository.addTrack(track)
        }
    }
}