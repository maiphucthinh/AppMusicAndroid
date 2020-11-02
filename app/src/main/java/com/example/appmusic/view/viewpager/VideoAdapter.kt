package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.InfomationVideoFragment
import com.example.appmusic.view.fragment.ListVideoChildFragment

class VideoAdapter(manager: FragmentManager):FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0-> return ListVideoChildFragment()
            else-> return InfomationVideoFragment()
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