package com.example.appmusic.view

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log

class MediaOnline : MediaPlayer(), MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    var player: MediaPlayer? = null

    fun setDataSoure(context: Context, link: String) {
        player = MediaPlayer()
        player!!.setOnErrorListener(this)
        player!!.setDataSource(context, Uri.parse(link))
        player!!.setOnPreparedListener(this)
        player!!.prepareAsync()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d("MusicOnline", "onError.......")
        return true
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp!!.start()
    }

    fun play() {
        if (player == null) {
            return
        }
        if (player!!.isPlaying) {
            return
        }
        player!!.start()
    }

    override fun pause() {
        if (player == null) {
            return
        }
        player!!.pause()
    }
    override fun stop   (){
        if (player == null) {
            return
        }
        player!!.stop()
    }

    override fun release() {
        player?.release()
        player = null
    }
}