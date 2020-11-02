package com.example.appmusic.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.appmusic.view.fragment.AlbumTabArtistFragment
import com.example.appmusic.view.fragment.MVTabArtistFragment
import com.example.appmusic.view.fragment.PlaylistTabArtistFragment
import com.example.appmusic.view.fragment.SongTabArtistFragment

class ParentPageArtistAdapter(fragment:FragmentManager) :
    FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return SongTabArtistFragment()
            1 -> return MVTabArtistFragment()
            2 -> return AlbumTabArtistFragment()
            else -> return PlaylistTabArtistFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Bài hát"
            1 -> return "Video"
            2 -> return "Album"
            3 -> return "Playlist"
        }
        return super.getPageTitle(position)
    }
}