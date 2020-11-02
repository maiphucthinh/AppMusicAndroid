package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.DiscoverFragment
import com.example.appmusic.view.fragment.HomeFragment
import com.example.appmusic.view.fragment.MVFragment
import com.example.appmusic.view.fragment.TabAllSearchSongFragment

class MainAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    var hom: HomeFragment
    var dis: DiscoverFragment
    var mv: MVFragment
    var pa: ParentSearchSong

    init {
        hom = HomeFragment()
        dis = DiscoverFragment()
        mv = MVFragment()
        pa = ParentSearchSong()
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return hom
            1 -> return dis
            2 -> return mv
            else -> return pa
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Cá nhân"
            1 -> return "Khám phá"
            2 -> return "MV"
            3 -> return "Tìm kiếm"
        }

        return super.getPageTitle(position)
    }
}