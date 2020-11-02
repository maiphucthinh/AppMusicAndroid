package com.example.appmusic.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusic.model.*
import com.thin.music.model.GetLinkMusic
import com.thin.music.model.ItemMusicList
import com.thin.music.model.ItemSearchOnline
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongViewModel : ViewModel {
    private val songSevice: SongSevice
    val songData: MutableLiveData<MutableList<ItemSong>>
    val listVideo: MutableLiveData<MutableList<ItemSearchOnline>>
    val listSearchAlbums: MutableLiveData<MutableList<ItemSearchOnline>>
    val listSearchAll: MutableLiveData<MutableList<ItemMusicList<ItemSong>>>
    val linkSong: MutableLiveData<GetLinkMusic>
    val linkVideoOnline: MutableLiveData<GetLinkMusic>
    val listThemeSearch: MutableLiveData<MutableList<ItemSong>>
    val listTheme: MutableLiveData<MutableList<ItemSong>>
    val listArtistSong: MutableLiveData<MutableList<ItemMusicList<ItemSong>>>
    val isRuning: ObservableBoolean
    val isRuningSearch:MutableLiveData<Boolean> = MutableLiveData()
    val isRuningVideo:MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingTheme:MutableLiveData<Boolean> = MutableLiveData()
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
        linkSong = MutableLiveData()
        listTheme = MutableLiveData()
        listThemeSearch = MutableLiveData()
        listArtistSong = MutableLiveData()
        isRuningSearch.value = false
        isRuningVideo.value = false
        isLoadingTheme.value = false

        songSevice = RetrofitUtil.creareRetrofit(
//            "https://songserver.herokuapp.com",
            "https://25811374ec3b.ngrok.io",
//            "https://musiconline98.herokuapp.com",
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

    fun linkSong(link: String, finish:(fullLink:GetLinkMusic)->String, error:(error:Throwable)->Unit) {
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
}