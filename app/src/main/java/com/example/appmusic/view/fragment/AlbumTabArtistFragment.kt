package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appmusic.databinding.SongTabArtistFragmentBinding
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding

class AlbumTabArtistFragment:Fragment() {
    private lateinit var binding: SongTabArtistFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongTabArtistFragmentBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }
}