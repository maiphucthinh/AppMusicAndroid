package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.InfomationVideoAdapter
import com.thin.music.model.GetLinkMusic

class InfomationVideoFragment : Fragment(), InfomationVideoAdapter.IInfomation {
    private lateinit var binding: TabSearchPlayListFragmentBinding
    private lateinit var data: GetLinkMusic
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TabSearchPlayListFragmentBinding.inflate(
            inflater, container, false
        )
        binding.prg.visibility = View.GONE
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkVideoOnline.observe(this, Observer {
            val lyric = it.lyric
            val artistName = it.artistName
            val authorName = it.authorName
            data = GetLinkMusic(lyric, artistName, authorName)
            binding.rcAlbums.layoutManager = LinearLayoutManager(context)
            binding.rcAlbums.adapter = InfomationVideoAdapter(this)

        })
    }

    override fun getSizeInfo() = 1

    override fun getDataInfo(position: Int): GetLinkMusic {
        return data
    }
}