package com.example.songslistingapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.songslistingapp.datastore.SongsDatabase
import com.example.songslistingapp.network.SongsApi
import com.example.songslistingapp.util.SharedPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application): SongsDatabase =
        Room.databaseBuilder(
            app,
            SongsDatabase::class.java,
            "songs_db"
        ).build()

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): SongsApi = retrofit.create(SongsApi::class.java)

    @Provides
    @Singleton
    fun providesSharedPrefHelper(app: Application): SharedPrefHelper =
        SharedPrefHelper(app.getSharedPreferences("app_pref", Context.MODE_PRIVATE))

}