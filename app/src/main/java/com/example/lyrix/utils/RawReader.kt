package com.example.lyrix.utils

import android.content.Context
import org.json.JSONObject

class RawReader(private val context: Context) {
    private fun readRawFile(): String {
        val dataResourceId = context.resources.getIdentifier("props", "raw", context.packageName)
        return context.resources.openRawResource(dataResourceId).bufferedReader()
            .use { it.readText() }
    }

    fun <T> getProperty(key: String): T {
        val data = readRawFile()
        return JSONObject(data).getJSONObject("props").get(key) as T
    }
}