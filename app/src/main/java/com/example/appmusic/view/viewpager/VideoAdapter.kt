package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.InfomationVideoFragment
import com.example.appmusic.view.fragment.ListVideoChildFragment

class VideoAdapter(manager: FragmentManager):FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    var lisVideo:ListVideoChildFragment
    init {
        lisVideo = ListVideoChildFragment()
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> lisVideo
            else-> InfomationVideoFragment()
        }
    }

    override fun getCount(): Int {
       return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Tiếp theo"
            1 -> return "Thông tin"
        }

        return super.getPageTitle(position)
    }
}