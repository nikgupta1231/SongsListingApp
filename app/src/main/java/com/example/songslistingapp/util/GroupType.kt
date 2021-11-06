package com.example.songslistingapp.util

sealed class GroupType {
    object GroupByAlbumName : GroupType()
    object GroupByArtistName : GroupType()
}