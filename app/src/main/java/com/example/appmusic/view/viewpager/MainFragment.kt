package com.example.appmusic.view.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.R
import com.example.appmusic.databinding.ActivityMainBinding
import com.example.appmusic.databinding.FragmentMainBinding
import com.example.appmusic.databinding.TabPlaylistSongBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.fragment.TabAllSearchSongFragment

class MainFragment : Fragment() {
    private lateinit var activitys: MainActivity
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(
            inflater, container, false
        )
        activitys = (activity as MainActivity)
        binding.vp.adapter = MainAdapter(childFragmentManager)
        binding.tab.setupWithViewPager(
            binding.vp
        )
        setupTabIcons()
        binding.vp.currentItem = 1
        return binding.root
    }

     fun getParentSearchSong():ParentSearchSong{
        return (binding.vp.adapter as MainAdapter).pa
    }

    fun setupTabIcons() {
        binding.tab.getTabAt(0)!!.setIcon(R.drawable.baseline_library_music_white_18dp)
        binding.tab.getTabAt(1)!!.setIcon(R.drawable.baseline_adjust_white_18dp)
        binding.tab.getTabAt(2)!!.setIcon(R.drawable.baseline_slideshow_white_18dp)
        binding.tab.getTabAt(3)!!.setIcon(R.drawable.baseline_search_white_18dp)
    }
}