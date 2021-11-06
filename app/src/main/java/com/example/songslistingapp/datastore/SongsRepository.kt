package com.example.songslistingapp.datastore

import androidx.room.withTransaction
import com.example.songslistingapp.network.SongsApi
import com.example.songslistingapp.util.SharedPrefHelper
import com.example.songslistingapp.util.networkBoundResource
import javax.inject.Inject

class SongsRepository @Inject constructor(
    private val db: SongsDatabase,
    private val api: SongsApi,
    private val prefHelper: SharedPrefHelper
) {

    companion object {
        private const val PREF_LAST_UPDATE_TIME_STAMP = "last_update_ts"
        private const val UPDATE_DURATION = 60 * 60 * 1000
    }

    private val dao = db.songsListDao()

    fun getSongs() = networkBoundResource(
        query = {
            dao.getSongListSortedByAlbums()
        },
        fetch = {
            api.getSongsList().payload.songList
        },
        saveFetchResult = { songs ->
            db.withTransaction {
                prefHelper.putLong(PREF_LAST_UPDATE_TIME_STAMP, System.currentTimeMillis())
                dao.deleteAllSongs()
                dao.updateSongs(songs)
            }
        },
        shouldFetch = {
            if (it.isEmpty()) true
            else (System.currentTimeMillis() - prefHelper.getLong(PREF_LAST_UPDATE_TIME_STAMP) > UPDATE_DURATION)
        }
    )

}