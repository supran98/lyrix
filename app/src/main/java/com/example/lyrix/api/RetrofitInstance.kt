package com.example.lyrix.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val baseUrl = "https://api.musixmatch.com/ws/1.1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: MusixmatchApi by lazy {
        retrofit.create(MusixmatchApi::class.java)
    }
}