package com.example.appmusic.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.appmusic.R
import com.example.appmusic.model.ItemSong
import com.example.appmusic.viewmodel.SongViewModel

class SongService : Service(), MediaOnline.IMusicOnline {
    private lateinit var mediaOnline: MediaOnline
    private val idChannel = "MY_CHANEL"
    private var currentSong: ItemSong? = null
    var currentPossition = 0
    var model : SongViewModel? = null
    var itemSong = mutableListOf<ItemSong>()
    var inter: MediaOnline.IMusicOnline? = null
    override fun onCreate() {
        super.onCreate()
        mediaOnline = MediaOnline()
        mediaOnline.inter = this
    }
    override fun onBind(intent: Intent?): IBinder? {
        return MyBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            handlerIntent(intent)
        }
        return START_NOT_STICKY
    }

    private fun handlerIntent(intent: Intent) {
        val action = intent.action
        if (action == null) {
            return
        }
        when (action) {
            "PREVIOUS" -> {
                    if (currentPossition == 0) {
                        currentPossition = itemSong.size - 1
                    } else currentPossition -= 1
                model?.linkSong(itemSong[currentPossition].linkSong)

            }
            "PLAY" -> {
                if (mediaOnline.isPrepare()) {
                    if (mediaOnline.player!!.isPlaying) {
                        mediaOnline.pause()
                        if (currentSong != null) {
                            createNotification(currentSong!!, false)
                        }

                    } else {
                        mediaOnline.play()
                        createNotification(currentSong!!)
                    }
                }
            }
            "NEXT" -> {
                if (currentPossition == itemSong.size-1) {
                    currentPossition = 0
                } else currentPossition += 1
                model?.linkSong(itemSong[currentPossition].linkSong)
            }
        }
    }

    fun release() {
        mediaOnline.release()
    }

    fun setDataSoure(song: ItemSong) {
        currentSong = song
        mediaOnline.setDataSoure(this, song.linkSong)
        createNotification(song)
    }

    fun pause() {
        mediaOnline.pause()
    }

    fun player(): MediaPlayer? {
        return mediaOnline.player
    }

    fun play() {
        mediaOnline.play()
    }

    fun isPrepare(): Boolean {
        return mediaOnline.isPrepare()
    }

    fun getTotalTime(): Int {
        return mediaOnline.getTotalTime()
    }

    fun getCurentTime(): Int? {
        return mediaOnline.getCurentTime()
    }

    class MyBinder : Binder {
        val service: SongService

        constructor(service: SongService) {
            this.service = service
        }
    }

    override fun onPrepared() {
        inter?.onPrepared()
    }

    fun createNotification(song: ItemSong, isPlay: Boolean = true) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                idChannel, "MY_CHANEL", NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(chanel)
        }
        val no = NotificationCompat.Builder(
            this, idChannel
        )
        val remote = RemoteViews(
            packageName,
            R.layout.notification_song_media
        )
        val pI = Intent()
        pI.setClass(this, MainActivity::class.java)
        val p = PendingIntent.getActivity(this, 0, pI, 0)
        no.setContentIntent(p)
        Glide.with(this)
            .asBitmap()
            .load(song.linkImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    remote.setImageViewBitmap(R.id.iv_img, resource)
                    updatePendingIntent(remote)
                    startForeground(1, no.build())
                }

            })
        if (isPlay) {
            remote.setImageViewResource(R.id.btn_play, R.drawable.baseline_pause_black_24dp)
        } else {
            remote.setImageViewResource(R.id.btn_play, R.drawable.baseline_play_arrow_black_24dp)
        }
        remote.setTextViewText(R.id.tv_name, song.songName)
        remote.setTextViewText(R.id.tv_artist, song.artistName)
        no.setSmallIcon(R.drawable.baseline_music_note_white_24dp)
        no.setCustomBigContentView(remote)

        updatePendingIntent(remote)
        startForeground(1, no.build())
    }

    private fun updatePendingIntent(remote: RemoteViews) {
        val intentPrevious = Intent()
        intentPrevious.setAction("PREVIOUS")
        intentPrevious.setClass(this, SongService::class.java)
        val pendingPrevious = PendingIntent.getService(this, 1, intentPrevious, 0)

        val intentPlay = Intent()
        intentPlay.setAction("PLAY")
        intentPlay.setClass(this, SongService::class.java)
        val pendingPlay = PendingIntent.getService(this, 1, intentPlay, 0)

        val intentNext = Intent()
        intentNext.setAction("NEXT")
        intentNext.setClass(this, SongService::class.java)
        val pendingNext = PendingIntent.getService(this, 1, intentNext, 0)

        remote.setOnClickPendingIntent(R.id.btn_previous, pendingPrevious)
        remote.setOnClickPendingIntent(R.id.btn_play, pendingPlay)
        remote.setOnClickPendingIntent(R.id.btn_next, pendingNext)

    }

}