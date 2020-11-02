package com.thin.music.model

class GetLinkMusic {
    var link = ""
    var lyric = ""
    var songName = ""
    var artistName = ""
    var linkArtist = ""
    var authorName = ""
    var linkAuthor = ""

    constructor(lyric: String, artistName: String, authorName: String) {
        this.lyric = lyric
        this.artistName = artistName
        this.authorName = authorName
    }

    constructor(lyric: String, artistName: String, authorName: String, songName: String) {
        this.lyric = lyric
        this.artistName = artistName
        this.authorName = authorName
        this.songName = songName
    }
}