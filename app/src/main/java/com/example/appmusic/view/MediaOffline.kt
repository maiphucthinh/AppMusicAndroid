package com.example.appmusic.view

import android.media.MediaPlayer
import android.util.Log

class MediaOffline : MediaPlayer.OnErrorListener {
    var inter: MediaOnline.IMusicMedia? = null
    var player: MediaPlayer? = null

    fun setDataSource(path: String) {
        player = MediaPlayer()
        player!!.setOnErrorListener(this)
        player!!.setDataSource(path)
        player!!.prepare()
        inter!!.onPrepared()
        player!!.start()
    }
    fun getTotalTime():Int{
       return player!!.duration
    }
    fun getCurrentTime():Int{
        return player!!.currentPosition
    }

    fun play() {
        if (player == null) {
            return
        }
        if (!player!!.isPlaying) {
            player!!.start()
        }
    }

    fun pause() {
        if (player == null) {
            return
        }
        if (player!!.isPlaying) {
            player!!.pause()
        }
    }

    fun stop() {
        if (player == null) {
            return
        }
        if (player!!.isPlaying) {
            player!!.stop()
        }
    }

    fun release() {
        player?.release()
        player = null
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d("MusicOffline", "onError.......")
        return true
    }
}