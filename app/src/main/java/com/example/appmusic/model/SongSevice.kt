package com.example.appmusic.model

import com.thin.music.model.ItemMusicList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SongSevice {
//    @GET("/api/searchSong")
////    @GET("api/getSong")
//    fun searchSong(
//        @Query(value = "songName") songName: String?
//    ): Observable<MutableList<ItemSong>>

    @GET("/api/searchSong")
    fun searchSong(
        @Query(value = "songName") songName: String?
    ): Observable<MutableList<ItemSong>>

    @GET("/api/linkMusic")
    fun getLinkSong(
        @Query(value = "linkSong") linkSong: String?
    ): Observable<LinkSong>

    @GET("/api/getChart")
    fun getChart(): Observable<MutableList<ItemMusicList<ItemSong>>>
}