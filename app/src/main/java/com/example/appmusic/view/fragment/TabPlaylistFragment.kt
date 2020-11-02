package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.TabPlaylistSongBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter

class TabPlaylistFragment : Fragment(), ChildDiscoverChartAdapter.IChart {
    private lateinit var binding: TabPlaylistSongBinding
    private var listSongs: MutableList<ItemSong>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabPlaylistSongBinding.inflate(
            inflater, container, false
        )
        listSongs = (activity as MainActivity).getListSong()
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter = ChildDiscoverChartAdapter(this)
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkSong.observe(this, Observer {
            binding.rc.adapter?.notifyDataSetChanged()
        })
    }

    override fun getSizeChart(): Int {
        if (listSongs == null) {
            return 0
        }
        return listSongs!!.size
    }

    override fun getListChart(position: Int): ItemSong {
        return listSongs!![position]
    }

    override fun setOnclickItemChart(position: Int) {
        (activity as MainActivity).getModel().linkSong(
            listSongs!![position].linkSong
        )
        (activity as MainActivity).initMedia(position, listSongs!!)
    }
}