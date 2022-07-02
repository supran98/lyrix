package com.example.lyrix.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lyrix.model.Track
import com.example.lyrix.model.TrackData
import com.example.lyrix.model.TrackWrapper
import com.example.lyrix.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RetrofitViewModel(private val repository: ApiRepository) : ViewModel() {
    val lyrics: MutableLiveData<String> = MutableLiveData()
    val trackList: MutableLiveData<List<Track>> = MutableLiveData()

    fun getSortedTracks(query: String) {
        viewModelScope.launch {
            try {
                val response: List<TrackWrapper> = repository.getSortedTracks(query)
                trackList.value = response.map { it.track }
            } catch (e: Exception) {
                trackList.value = emptyList()
            }
        }
    }

    fun getLyrics(commontrackId: Int) {
        viewModelScope.launch {
            try {
                val response: Response<TrackData> = repository.getLyrics(commontrackId)
                if (response.isSuccessful) {
                    lyrics.value = response.body()!!.message.body.lyrics.lyrics_body
                }
            } catch (e: Exception) {
                lyrics.value = "Could not find lyrics for this song :("
            }
        }
    }
}