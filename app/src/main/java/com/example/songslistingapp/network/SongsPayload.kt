package com.example.songslistingapp.network

import com.example.songslistingapp.datastore.entity.SongEntity

data class SongsPayload(val songList: List<SongEntity>)