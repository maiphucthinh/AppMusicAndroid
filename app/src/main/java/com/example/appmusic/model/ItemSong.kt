package com.example.appmusic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_song")
class ItemSong {
    @PrimaryKey
    var id: String = ""
    var linkImage = ""
    var number = ""
    var songName = ""
    var artistName = ""
    var linkSong = ""
    var linkMusic: String? = null
    var keySearch = ""
}