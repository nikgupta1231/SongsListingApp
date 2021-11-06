package com.example.songslistingapp.util

import android.content.SharedPreferences

class SharedPrefHelper(private val sharedPreferences: SharedPreferences) {

    fun putString(key: String, string: String) {
        sharedPreferences.edit().apply {
            putString(key, string)
        }.apply()
    }

    fun putLong(key: String, long: Long) {
        sharedPreferences.edit().apply {
            putLong(key, long)
        }.apply()
    }

    fun getString(key: String, defaultString: String? = null): String? =
        sharedPreferences.getString(key, defaultString)

    fun getLong(key: String, defaultLong: Long = 0): Long =
        sharedPreferences.getLong(key, defaultLong)

}