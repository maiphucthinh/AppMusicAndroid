package com.example.appmusic.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.ListThemeBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.SongOnlineAdapter

class ChildListThemeFragment : Fragment(), SongOnlineAdapter.ISongOnline {
    private var isCheckLoading = true
    private lateinit var binding: ListThemeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListThemeBinding.inflate(
            inflater, container, false
        )
        binding.rcTheme.layoutManager = LinearLayoutManager(context)
        binding.rcTheme.adapter = SongOnlineAdapter(this)
        (activity as MainActivity).addContentChild()
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listThemeSearch.observe(this, Observer {
            binding.rcTheme.adapter!!.notifyDataSetChanged()
            val totalSong = (activity as MainActivity).getModel().listThemeSearch.value!!.size
            binding.totalList.text = totalSong.toString() + " bài hát"
        })
        (activity as MainActivity).getModel().isLoadingTheme.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            } else
                binding.prg.visibility = View.GONE
        })
    }

    override fun getSize(): Int {
        if ((activity as MainActivity).getModel().listThemeSearch.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listThemeSearch.value!!.size
    }

    override fun getSongOnline(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listThemeSearch.value!![position]
    }

    override fun setOnClickItem(position: Int) {
        val listSong = (activity as MainActivity).getModel().listThemeSearch.value

        (activity as MainActivity).getModel().linkSong(
            (activity as MainActivity).getModel().listThemeSearch.value!![position].linkSong
        )
        if (listSong != null) {
            (activity as MainActivity).initMedia(position, listSong)
        }

    }

    override fun setOnClickMenu(position: Int, btnMenu: Button) {
    }
}