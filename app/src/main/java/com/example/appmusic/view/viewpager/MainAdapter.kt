package com.example.appmusic.view.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.DiscoverFragment
import com.example.appmusic.view.fragment.HomeFragment
import com.example.appmusic.view.fragment.MVFragment
import com.example.appmusic.view.fragment.SearchSong

class MainAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return DiscoverFragment()
            2 -> return MVFragment()
//            3 -> return SearchSong()
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Cá nhân"
            1 -> return "Khám phá"
            2 -> return "MV"
//            3 -> return "Search song"
        }
        return super.getPageTitle(position)
    }
}