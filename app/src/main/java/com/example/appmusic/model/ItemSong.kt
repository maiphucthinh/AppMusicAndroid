package com.example.appmusic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration

@Entity(tableName = "item_song")
class ItemSong {
    constructor(
        id: String?,
        linkImage: String?,
        songName: String,
        artistName: String,
        linkSong: String,
        duration: String?
    ) {
        if (id != null) {
            this.id = id
        }
        if (linkImage != null) {
            this.linkImage = linkImage
        }
        this.songName = songName
        this.artistName = artistName
        this.linkSong = linkSong
        if (duration != null) {
            this.duration = duration
        }
    }


    @PrimaryKey
    var id: String = ""
    var linkImage = ""
    var number = ""
    var songName = ""
    var artistName = ""
    var linkSong = ""
    var typeMusic = ""
    var duration = ""
    var linkSinger: String? = null
    var linkMusic: String? = null
    var linkAlbum: String? = null
    var albumName: String? = null
    var keySearch = ""
}