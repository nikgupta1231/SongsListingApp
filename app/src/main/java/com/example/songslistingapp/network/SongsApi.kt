package com.example.songslistingapp.network

import retrofit2.http.GET

interface SongsApi {

    @GET("v3/fb01393e-9ecb-474a-98ad-5349bbaaa629")
    suspend fun getSongsList(): SongsApiResponse

}