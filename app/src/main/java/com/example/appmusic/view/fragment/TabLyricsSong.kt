package com.example.appmusic.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.ShowLyricsBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.InfomationVideoAdapter
import com.example.appmusic.view.adapter.TabLyricSongAdapter
import com.thin.music.model.GetLinkMusic

class TabLyricsSong : Fragment(), TabLyricSongAdapter.ILyric {
    private lateinit var binding: ShowLyricsBinding
    private lateinit var data:GetLinkMusic
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShowLyricsBinding.inflate(
            inflater, container, false
        )
        register()

        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkSong.observe(this, Observer {
            val songName = "Bài hát: "+ it.songName
            val artistName = "Ca sĩ: "+it.artistName
            val lyricsSong = it.lyric
            val author = it.authorName
             data = GetLinkMusic(lyricsSong, artistName, author, songName)
            binding.rc.layoutManager = LinearLayoutManager(context)
            binding.rc.adapter = TabLyricSongAdapter(this)
        })
    }

    override fun getSizeLyric() = 1

    override fun getDataLyric(): GetLinkMusic {
        return data
    }
}