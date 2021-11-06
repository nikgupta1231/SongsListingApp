package com.example.songslistingapp.datastore.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "songs_list")
data class SongEntity(

    @PrimaryKey
    @ColumnInfo(name = "name")
    @SerializedName("Name")
    val name: String,

    @ColumnInfo(name = "artist")
    @SerializedName("Artist")
    val artist: String,

    @ColumnInfo(name = "album")
    @SerializedName("Album")
    val album: String

) : Serializable
