package com.example.appmusic.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song_offline")
class TableSongOffline {
    @PrimaryKey
    var id: Int? = null
    var image: Bitmap? = null
    var songName: String? = null
    var artistName: String? = null
    var lyricsSong: String? = null
}