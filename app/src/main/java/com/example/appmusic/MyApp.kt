package com.example.appmusic

import android.app.Application
import com.example.appmusic.viewmodel.AppModel

class MyApp : Application() {
    companion object {
        private lateinit var appModel: AppModel
        fun getAppModel() = appModel
    }

    override fun onCreate() {
        super.onCreate()
        appModel = AppModel()
    }
}