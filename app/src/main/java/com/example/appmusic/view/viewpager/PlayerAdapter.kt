package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.TabImageSongPlayer
import com.example.appmusic.view.fragment.TabLyricsSong
import com.example.appmusic.view.fragment.TabPlaylistFragment

class PlayerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TabPlaylistFragment()
            1 -> return TabImageSongPlayer()
            else -> return TabLyricsSong()
        }
    }

    override fun getCount() = 3

}