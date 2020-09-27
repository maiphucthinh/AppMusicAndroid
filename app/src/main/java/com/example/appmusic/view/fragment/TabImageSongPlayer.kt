package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.ImageSongPlayerBinding

class TabImageSongPlayer : Fragment() {
    private lateinit var binding: ImageSongPlayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImageSongPlayerBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}