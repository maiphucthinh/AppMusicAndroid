package com.example.appmusic.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusic.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SongViewModel : ViewModel {
    val songData: MutableLiveData<MutableList<ItemSong>>
    val linkSong: MutableLiveData<LinkSong>
    private val songSevice: SongSevice
    val isRuning: ObservableBoolean
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val chart: MutableLiveData<MutableList<ItemChart>>

    constructor() {
        isLoading.value = true
        isRuning = ObservableBoolean()
        isRuning.set(false)
        chart = MutableLiveData()
        songData = MutableLiveData()
        linkSong = MutableLiveData()

        songSevice = RetrofitUtil.creareRetrofit(
//            "https://songserver.herokuapp.com",
//            "https://serviceapp0902.herokuapp.com",
            "https://musiconline98.herokuapp.com",
            SongSevice::class.java
        )
    }

    fun searchSong(content: String) {
        isRuning.set(true)
        isLoading.value = true
        songSevice.searchSong(content)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRuning.set(false)
                    isLoading.value = false
                    songData.value = it
                },
                {
//                    isRuning.set(false)
                    it.printStackTrace()
                }
            )
    }

    fun linkSong(link: String) {
        isRuning.set(true)
        isLoading.value = true
        songSevice.getLinkSong(link)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isRuning.set(false)
                    isLoading.value = false
                    linkSong.value = it
                },
                {
                    isRuning.set(false)
                    it.printStackTrace()
                }
            )
    }

    fun getChart() {
        songSevice.getChart()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    chart.value = it
                },
                {
                    it.printStackTrace()
                }
            )
    }
}