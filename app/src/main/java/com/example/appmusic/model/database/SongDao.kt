package com.example.appmusic.model.database

import androidx.room.*
import com.example.appmusic.model.ItemSong
import com.example.appmusic.model.TableSongOffline


@Dao
interface SongDao {
    //
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//     fun insetAllSong(song: MutableList<ItemSong>)
//
//    @Query("delete from item_song where keySearch = :keySearch")
//     fun deleteSongByKeySearch(keySearch: String)
//
//    @Query("select * from item_song where keySearch = :keySearch")
//    open fun findSongByKeySearch(keySearch: String)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    open fun insetSong(song: ItemSong)
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    open fun insetSong(song: TableSongOffline   )
}