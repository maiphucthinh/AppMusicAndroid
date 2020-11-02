package com.example.appmusic.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.appmusic.R

import com.example.appmusic.databinding.ImageSongPlayerBinding
import com.example.appmusic.view.MainActivity


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
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkSong.observe(this, Observer {
            (activity as MainActivity).setImageForTabImageSong(binding.imgSong)
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}