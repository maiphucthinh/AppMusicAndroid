package com.example.appmusic.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.R
import com.example.appmusic.databinding.SearchSongBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainFragment
import com.example.appmusic.view.MediaOnline
import com.example.appmusic.view.MusicPlayer
import com.example.appmusic.view.adapter.SongOnlineAdapter

class SearchSong : Fragment(), SongOnlineAdapter.ISongOnline, View.OnClickListener {
    private lateinit var data: MainFragment
    private lateinit var binding: SearchSongBinding
    private lateinit var mediaOnline: MediaOnline
    private var isCheckLoading = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchSongBinding.inflate(
            inflater, container, false
        )
        mediaOnline = MediaOnline()
        data = (activity as MainFragment)
        binding.data = data.getModel()
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter =
            SongOnlineAdapter(this)
        binding.back.setOnClickListener(this)
        register()
        return binding.root
    }

    private fun register() {
        data.getModel().isLoading.observe(this, Observer {
//            binding.prg.isVisible = it
            isCheckLoading = it
        })

        data.getModel().songData.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
            Log.d("MainActivity", "observe link song: " + it.get(1).linkSong)
        })

        data.getModel().linkSong.observe(this, Observer {
            // Log.d("MainActivity", "observe link song: " + it.link)
            mediaOnline.release()
            mediaOnline.setDataSoure(context!!, it.link)
        })
    }

    override fun getSize(): Int {
        return data.getSize()
    }

    override fun getSongOnline(position: Int): ItemSong {
        return data.getItemSong(position)
    }

    override fun setOnClickItem(position: Int) {
        data.getModel().linkSong(
            data.getModel().songData.value!![position].linkSong
        )
//        if (!isCheckLoading) {
            val intent = Intent()
            intent.setClass(context!!, MusicPlayer::class.java)
            startActivity(intent)
//        }
    }

    override fun setOnClickMenu(position: Int, btnMenu: Button) {
        val menu = PopupMenu(context, btnMenu)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.show()
    }

    override fun onClick(v: View?) {
        data.openMain()
    }
}