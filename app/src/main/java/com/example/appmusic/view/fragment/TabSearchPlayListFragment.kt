package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemSearchPlayListAdapter

class TabSearchPlayListFragment : Fragment(), ItemSearchPlayListAdapter.IPlayList {
    private lateinit var binding: TabSearchPlayListFragmentBinding
    private var isCheckLoading = false
    private val parentIndex = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabSearchPlayListFragmentBinding.inflate(
            inflater, container, false
        )
        register()
        binding.rcAlbums.layoutManager = GridLayoutManager(context, 2)
        binding.rcAlbums.adapter = ItemSearchPlayListAdapter(this)
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSearchAll.observe(this, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
        (activity as MainActivity).getModel().isRuningSearch.observe(this, Observer {
            isCheckLoading = it
            isCheckLoading = it
            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            }
            binding.prg.visibility = View.GONE
        })
    }
    override fun getSizePlayList(): Int {
        val list = (activity as MainActivity).getModel().listSearchAll.value
        if (list == null) {
            return 0
        }
        return list!![parentIndex].values.size
    }

    override fun getListPlayList(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values[position]
    }

    override fun setOnClickItemPlayList(position: Int) {
        val link = (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values[position].linkAlbum
            (activity as MainActivity).getModel().getChildLinkTheme(link)
            (activity as MainActivity).openChildListTheme()

    }
}