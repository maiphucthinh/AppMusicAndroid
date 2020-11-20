package com.example.appmusic.viewmodel

import android.content.Context
import android.provider.MediaStore
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusic.model.ItemSong
import com.example.appmusic.model.RetrofitUtil
import com.example.appmusic.model.SongSevice
import com.thin.music.model.GetLinkMusic
import com.thin.music.model.ItemMusicList
import com.thin.music.model.ItemSearchOnline
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class SongViewModel : ViewModel {
    private val songSevice: SongSevice
    val songData: MutableLiveData<MutableList<ItemSong>>
    val listVideo: MutableLiveData<MutableList<ItemSearchOnline>>
    val listSuggestionsVideo: MutableLiveData<MutableList<ItemSearchOnline>>
    val listSearchAlbums: MutableLiveData<MutableList<ItemSearchOnline>>
    val listSearchAll: MutableLiveData<MutableList<ItemMusicList<ItemSong>>>
    val linkSong: MutableLiveData<GetLinkMusic>
    val lisSongOffline: MutableLiveData<MutableList<ItemSong>>
    val linkVideoOnline: MutableLiveData<GetLinkMusic>
    val listThemeSearch: MutableLiveData<MutableList<ItemSong>>
    val listTheme: MutableLiveData<MutableList<ItemSong>>
    val listArtistSong: MutableLiveData<MutableList<ItemMusicList<ItemSong>>>
    val isRuning: ObservableBoolean
    val isRuningSearch: MutableLiveData<Boolean> = MutableLiveData()
    val isRuningVideo: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingTheme: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val chart: MutableLiveData<MutableList<ItemMusicList<ItemSong>>>

    constructor() {
        isLoading.value = false
        isRuning = ObservableBoolean()
        isRuning.set(false)
        linkVideoOnline = MutableLiveData()
        listSearchAlbums = MutableLiveData()
        chart = MutableLiveData()
        listSearchAll = MutableLiveData()
        songData = MutableLiveData()
        listVideo = MutableLiveData()
        listSuggestionsVideo = MutableLiveData()
        linkSong = MutableLiveData()
        listTheme = MutableLiveData()
        listThemeSearch = MutableLiveData()
        lisSongOffline = MutableLiveData()
        listArtistSong = MutableLiveData()
        isRuningSearch.value = false
        isRuningVideo.value = false
        isLoadingTheme.value = false

        songSevice = RetrofitUtil.creareRetrofit(
//            "https://e465d978acd6.ngrok.io",
            "https://musiconline98.herokuapp.com",
            SongSevice::class.java
        )
    }

    fun linkSong(link: String?) {
        isRuning.set(true)
        isLoading.value = true
        songSevice.getLinkSong(link)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRuning.set(false)
                    linkSong.value = it
                    isLoading.value = false
                },
                {
                    isRuning.set(false)
                    it.printStackTrace()
                }
            )
    }

    fun linkSong(
        link: String,
        finish: (fullLink: GetLinkMusic) -> String,
        error: (error: Throwable) -> Unit
    ) {
        songSevice.getLinkSong(link)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    finish(it)
                },
                {
                    error(it)
                }
            )
    }

    fun getChart() {
        isLoading.value = true
        songSevice.getChart()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoading.value = false
                    chart.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getVideo() {
        isRuningVideo.value = true
        songSevice.getVideo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRuningVideo.value = false
                    listVideo.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getSuggestionsVideo() {
        isRuningVideo.value = true
        songSevice.getSuggestionsVideo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listSuggestionsVideo.value = it
                    isRuningVideo.value = false
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getChildLinkTheme(linkTheme: String?) {
        isLoadingTheme.value = true
        songSevice.getLinkTheme(linkTheme)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoadingTheme.value = false
                    listThemeSearch.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getLinkVideo(linkVideo: String?) {
        isLoading.value = true
        songSevice.getLinkVideo(linkVideo)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoading.value = false
                    linkVideoOnline.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getSearchAll(nameSong: String?) {
        isRuningSearch.value = true
        songSevice.getSearchAll(nameSong)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRuningSearch.value = false
                    listSearchAll.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getAllArtistSong(linkArtist: String?) {
        isLoading.value = true
        songSevice.getAllArtistSong(linkArtist)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoading.value = false
                    listArtistSong.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun getLinkTheme(linkTheme: String?) {
        isLoading.value = true
        songSevice.getListTheme(linkTheme)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isLoading.value = false
                    listTheme.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }

    fun loadAllMusicOffline(context: Context) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = context!!.contentResolver.query(
            uri, null, null, null, null
        )!!
        val indexID = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val indexPath = cursor.getColumnIndex("_data")
        val indexTitle = cursor.getColumnIndex("_display_name")
        val indexDuration = cursor.getColumnIndex(
            MediaStore.Audio.Media.DURATION
        )
        val indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
        val songs = mutableListOf<ItemSong>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val id = cursor.getString(indexID)
            val path = cursor.getString(indexPath)
            val title = cursor.getString(indexTitle)
            val songName = getSongName(title)
            val artistName = getArtistName(title)
            val duration = cursor.getString(indexDuration)
            val albumID = cursor.getString(indexAlbumId)
            val albumArtUri = "content://media/external/audio/albumart/$albumID"
            songs.add(ItemSong(id, albumArtUri, songName, artistName, path, duration))

            cursor.moveToNext()
        }
        cursor.close()
        songs.sortWith(Comparator { o1, o2 ->
            o1.songName.compareTo(o2.songName)
        })
        lisSongOffline.value = songs
    }

    private fun getSongName(songName: String): String {
        var name = ""
        for (i in songName.indices) {
            if (songName[i] == '-') {
                name = songName.substring(0, i)
                name = name.replace("%20", " ")
                break
            }
        }
        return name
    }

    private fun getArtistName(songName: String): String {
        var artist = ""
        var index = 0
        for (i in songName.indices) {
            if (songName[i] == '-' && index == 0) {
                index = i
            }
            if (songName[i] == '-' && index != i || songName[i] == '.') {
                artist = songName.substring(index + 1, i)
                artist = artist.replace("%20", " ")
                artist.trim()
                break
            }
        }
        return artist
    }
}