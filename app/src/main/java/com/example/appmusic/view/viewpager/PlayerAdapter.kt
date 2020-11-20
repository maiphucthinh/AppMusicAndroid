package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.TabImageSongPlayer
import com.example.appmusic.view.fragment.TabLyricsSong
import com.example.appmusic.view.fragment.TabPlaylistFragment

class PlayerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var playList: TabPlaylistFragment
    var img: TabImageSongPlayer
    var lyrics: TabLyricsSong

    init {
        playList = TabPlaylistFragment()
        img = TabImageSongPlayer()
        lyrics = TabLyricsSong()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return playList
            1 -> return img
            else -> return lyrics
        }
    }

    override fun getCount() = 3

}