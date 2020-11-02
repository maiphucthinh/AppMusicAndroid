package com.example.appmusic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_song")
class ItemSong {
    constructor(linkImage: String?, songName: String, artistName: String, linkSong: String) {
        if (linkImage != null) {
            this.linkImage = linkImage
        }
        this.songName = songName
        this.artistName = artistName
        this.linkSong = linkSong
    }

    @PrimaryKey
    var id: String = ""
    var linkImage = ""
    var number = ""
    var songName = ""
    var artistName = ""
    var linkSong = ""
    var typeMusic = ""
    var linkSinger: String? = null
    var linkMusic: String? = null
    var linkAlbum: String? = null
    var albumName: String? = null
    var keySearch = ""
}