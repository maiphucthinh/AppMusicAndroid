package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemSearchArtistAdapter

class TabSearchArtistFragment : Fragment(), ItemSearchArtistAdapter.IChildArtist {
    private lateinit var binding: TabSearchPlayListFragmentBinding
    private val parentIndex = 3
    private var isCheckLoading = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabSearchPlayListFragmentBinding.inflate(
            inflater, container, false
        )
        register()
        binding.rcAlbums.layoutManager = LinearLayoutManager(context)
        binding.rcAlbums.adapter = ItemSearchArtistAdapter(this)
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSearchAll.observe(this, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
        (activity as MainActivity).getModel().isRuningSearch.observe(this, Observer {
            isCheckLoading = it
            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            }
            binding.prg.visibility = View.GONE
        })
    }

    override fun getSizeArtist(): Int {
        val list = (activity as MainActivity).getModel().listSearchAll.value
        if (list == null) {
            return 0
        }
        return list!![parentIndex].values.size
    }

    override fun getListArtist(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values[position]
    }

    override fun setOnClickItemArtist(position: Int) {
        val link =
            (activity as MainActivity)
                .getModel().listSearchAll.value!![parentIndex].values[position].linkSinger
        (activity as MainActivity).openParentPageArtist()
        (activity as MainActivity).getModel().getAllArtistSong(link)
    }
}