package com.example.songslistingapp.datastore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.songslistingapp.datastore.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsListDao {

    @Query("DELETE FROM songs_list")
    suspend fun deleteAllSongs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSongs(songsList: List<SongEntity>)

    @Query("SELECT * FROM songs_list ORDER BY album ASC")
    fun getSongListSortedByAlbums(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs_list ORDER BY artist ASC")
    fun getSongListSortedByArtist(): Flow<List<SongEntity>>

}