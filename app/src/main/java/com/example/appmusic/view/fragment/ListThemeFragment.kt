package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemSearchPlayListAdapter

class ListThemeFragment : Fragment(), ItemSearchPlayListAdapter.IPlayList {
    private lateinit var binding: TabSearchPlayListFragmentBinding
    private var isCheckLoading = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabSearchPlayListFragmentBinding.inflate(
            inflater, container, false
        )
        binding.rcAlbums.layoutManager = GridLayoutManager(context,2)
        binding.rcAlbums.adapter = ItemSearchPlayListAdapter(this)
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listTheme.observe(this, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
        (activity as MainActivity).getModel().isLoading.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            } else
                binding.prg.visibility = View.GONE
        })
    }

    override fun getSizePlayList(): Int {
        if ((activity as MainActivity).getModel().listTheme.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listTheme.value!!.size
    }

    override fun getListPlayList(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listTheme.value!![position]
    }

    override fun setOnClickItemPlayList(position: Int) {
       val linkTheme = (activity as MainActivity).getModel().listTheme.value!![position].linkAlbum
        (activity as MainActivity).openChildListTheme()
        (activity as MainActivity).getModel().getChildLinkTheme(linkTheme)
    }
}