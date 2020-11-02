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
import com.example.appmusic.view.adapter.ItemVideoAdapter

class TabSearchVideoFragment : Fragment(), ItemVideoAdapter.IVideo {
    private lateinit var binding: TabSearchPlayListFragmentBinding
    private var isCheckLoading = false
    private val index = 2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabSearchPlayListFragmentBinding.inflate(
            inflater, container, false
        )
        binding.rcAlbums.layoutManager = LinearLayoutManager(context)
        binding.rcAlbums.adapter =
            ItemVideoAdapter(this)
        register()
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

    override fun getSizeVideo(): Int {
        val list = (activity as MainActivity).getModel().listSearchAll.value
        if (list == null) {
            return 0
        }
        return list!![index].values.size
    }

    override fun getListVideo(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listSearchAll.value!![index].values[position]
    }

    override fun setOnClickItemVideo(position: Int) {
        val link =
            (activity as MainActivity).getModel().listSearchAll.value!![index].values[position].linkSong
        (activity as MainActivity).getModel().getLinkVideo(link)
        (activity as MainActivity).openVideo(position)
    }


}