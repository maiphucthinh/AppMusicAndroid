package com.example.appmusic.model

import com.thin.music.model.GetLinkMusic
import com.thin.music.model.ItemMusicList
import com.thin.music.model.ItemSearchOnline
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SongSevice {

    @GET("/api/getLink")
    fun getLinkSong(
        @Query(value = "linkSong") linkSong: String?
    ): Observable<GetLinkMusic>

    @GET("/api/getChart")
    fun getChart(): Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/getVideo")
    fun getVideo(): Observable<MutableList<ItemSearchOnline>>

    @GET("/api/getTheme")
    fun getLinkTheme(
        @Query(value = "linkTheme") linkTheme: String?
    ): Observable<MutableList<ItemSong>>

    @GET("/api/getLinkVideo")
    fun getLinkVideo(
        @Query(value = "linkVideo") linkVideo: String?
    ): Observable<GetLinkMusic>

    @GET("/api/searchAll")
    fun getSearchAll(
        @Query(value = "nameSong") nameSong: String?
    ): Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/getAllArtistSong")
    fun getAllArtistSong(
        @Query(value = "linkArtist") linkArtist: String?
    ): Observable<MutableList<ItemMusicList<ItemSong>>>

    @GET("/api/getListTheme")
    fun getListTheme(
        @Query(value = "linktheme") linktheme: String?
    ): Observable<MutableList<ItemSong>>

}