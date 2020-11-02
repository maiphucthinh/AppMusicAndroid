package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.*

class ParentSearchSongAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TabAllSearchSongFragment()
            1 -> return TabSearchSongFragment()
            2 -> return TabSearchPlayListFragment()
            3 -> return TabSearchVideoFragment()
            else -> return TabSearchArtistFragment()
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Tất cả"
            1 -> return "Bài hát"
            2 -> return "PlayList"
            3 -> return "MV"
            4 -> return "Nghệ sĩ"
        }
        return super.getPageTitle(position)
    }
}