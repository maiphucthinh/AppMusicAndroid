package com.example.appmusic.view

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log

class MediaOnline : MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    var inter: IMusicMedia? = null
    var player: MediaPlayer? = null
    private var isPrepare = false


    fun setDataSource(context: Context, link: String) {
        player = MediaPlayer()
        player!!.setOnErrorListener(this)
        player!!.setDataSource(context, Uri.parse(link))
        player!!.setOnPreparedListener(this)
        isPrepare = false
        player!!.prepareAsync()
    }
    fun isPrepare() = isPrepare

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d("MusicOnline", "onError.......")
        return true
    }

    fun getTotalTime(): Int {
        return player!!.duration
    }

    fun getCurentTime(): Int {
        return player!!.currentPosition
    }

    override fun onPrepared(mp: MediaPlayer) {
        isPrepare = true
        inter?.onPrepared()
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

    fun pause() {
        if (player == null) {
            return
        }
        player!!.pause()
    }

    fun stop() {
        if (player == null) {
            return
        }
        player!!.stop()
    }

    fun release() {
        isPrepare = false
        player?.release()
        player = null
    }

    interface IMusicMedia {
        fun onPrepared()
        fun onCompletion()
    }
}