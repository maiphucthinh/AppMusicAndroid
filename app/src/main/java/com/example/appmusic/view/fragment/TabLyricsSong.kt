package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.ImageSongPlayerBinding
import com.example.appmusic.databinding.ShowLyricsBinding

class TabLyricsSong : Fragment() {
    private lateinit var binding: ShowLyricsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowLyricsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}