package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.SongTabArtistFragmentBinding
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemSearchPlayListAdapter

class AlbumTabArtistFragment : Fragment(), ItemSearchPlayListAdapter.IPlayList {
    private lateinit var binding: SongTabArtistFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongTabArtistFragmentBinding.inflate(
            inflater, container, false
        )
        binding.rcAlbums.layoutManager = LinearLayoutManager(context)
        binding.rcAlbums.adapter = ItemSearchPlayListAdapter(this)
        return binding.root
    }

    override fun getSizePlayList(): Int {
        if ((activity as MainActivity).getModel().listArtistSong.value!![1] == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listArtistSong.value!![1].values.size
    }

    override fun getListPlayList(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listArtistSong.value!![1].values[position]
    }

    override fun setOnClickItemPlayList(position: Int) {
        TODO("Not yet implemented")
    }
}