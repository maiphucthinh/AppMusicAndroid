package com.example.appmusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppModel : ViewModel() {
    val actionMedia: MutableLiveData<String>

    init {
        actionMedia = MutableLiveData()
    }

    fun postAction(type:String){
        actionMedia.value = type
    }
}