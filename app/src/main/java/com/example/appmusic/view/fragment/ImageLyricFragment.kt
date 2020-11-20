package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.FragmentImageLyricBinding
import com.example.appmusic.view.viewpager.PlayerAdapter

class ImageLyricFragment : Fragment() {
    private lateinit var binding: FragmentImageLyricBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageLyricBinding.inflate(inflater, container, false)
        binding.tb.setupWithViewPager(binding.vp)
        binding.vp.adapter = PlayerAdapter(childFragmentManager)
        binding.vp.currentItem = 1
        return binding.root
    }

    fun setCurrentIndex(index: Int) {
        binding.vp.currentItem = index
    }

    fun getImagePlayer(): TabImageSongPlayer {
        return (binding.vp.adapter as PlayerAdapter).img
    }
    fun getplayList(): TabPlaylistFragment {
        return (binding.vp.adapter as PlayerAdapter).playList
    }
    fun getTabLyric(): TabLyricsSong {
        return (binding.vp.adapter as PlayerAdapter).lyrics
    }
}