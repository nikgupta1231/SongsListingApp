package com.example.songslistingapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.songslistingapp.datastore.SongsRepository
import com.example.songslistingapp.datastore.entity.SongEntity
import com.example.songslistingapp.util.GroupType
import com.example.songslistingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(songsRepository: SongsRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private var data: List<SongEntity> = emptyList()

    val dataLiveData = MutableLiveData<List<SongGroup>?>()
    val progressState = MutableLiveData<Resource<Boolean>>()
    var groupType: GroupType = GroupType.GroupByAlbumName
    var groupSpanCount: Int = 2

    init {
        songsRepository.getSongs().asLiveData().observeForever {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "initData: is loading and has data: ${it.data}")
                    dataLiveData.value = emptyList()
                    progressState.value = Resource.Loading(true)
                }
                is Resource.Success -> {
                    Log.d(TAG, "initData: is not loading and has data: ${it.data}")

                    if (it.data == null || it.data.isEmpty()) {
                        progressState.value = Resource.Error(Throwable("No Data found"), true)
                        return@observeForever
                    }

                    progressState.value = Resource.Success(true)
                    data = it.data
                    dataLiveData.value = when (groupType) {
                        GroupType.GroupByAlbumName -> getDataGroupedByAlbum(it.data)
                        GroupType.GroupByArtistName -> getDataGroupedByArtist(it.data)
                    }

                }
                is Resource.Error -> {
                    Log.d(TAG, "initData: is failed. error: ${it.error} and data: ${it.data}")
                    progressState.value = Resource.Error(Throwable("No Data found"), true)
                    dataLiveData.value = null
                }
            }
        }
    }

    fun updateGroupType(groupType: GroupType) {
        if (groupType == this.groupType) return
        this.groupType = groupType
        dataLiveData.value = when (groupType) {
            GroupType.GroupByAlbumName -> getDataGroupedByAlbum(data)
            GroupType.GroupByArtistName -> getDataGroupedByArtist(data)
        }
    }

    private fun getDataGroupedByAlbum(list: List<SongEntity>): List<SongGroup> {
        val map = TreeMap<String, MutableList<SongEntity>>()
        for (song in list) {
            if (map.containsKey(song.album)) {
                map[song.album]?.add(song)
            } else map[song.album] = arrayListOf(song)
        }
        return mapToList(map)
    }

    private fun getDataGroupedByArtist(list: List<SongEntity>): List<SongGroup> {
        val map = TreeMap<String, MutableList<SongEntity>>()
        for (song in list) {
            if (map.containsKey(song.artist)) {
                map[song.artist]?.add(song)
            } else map[song.artist] = arrayListOf(song)
        }
        return mapToList(map)
    }

    private fun mapToList(map: TreeMap<String, MutableList<SongEntity>>): List<SongGroup> {
        val groupList = ArrayList<SongGroup>()
        for (key in map.keys) {
            groupList.add(SongGroup().apply {
                groupName = key
                songList = map[key]
            })
        }
        return groupList
    }

}