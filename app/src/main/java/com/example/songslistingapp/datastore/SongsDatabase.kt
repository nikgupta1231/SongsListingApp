package com.example.songslistingapp.datastore

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.songslistingapp.datastore.dao.SongsListDao
import com.example.songslistingapp.datastore.entity.SongEntity

@Database(entities = [SongEntity::class], version = 1)
abstract class SongsDatabase : RoomDatabase() {

    abstract fun songsListDao(): SongsListDao

}