package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.TabImageSongPlayer
import com.example.appmusic.view.fragment.TabLyricsSong

class PlayerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TabImageSongPlayer()
            1 -> return TabLyricsSong()
            else -> return TabImageSongPlayer()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "."
            1 -> return "."
        }
        return super.getPageTitle(position)
    }
}