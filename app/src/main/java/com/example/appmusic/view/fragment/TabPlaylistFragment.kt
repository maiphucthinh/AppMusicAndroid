package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.R
import com.example.appmusic.databinding.TabPlaylistSongBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter

class TabPlaylistFragment : Fragment(), ChildDiscoverChartAdapter.IChart {
    private lateinit var binding: TabPlaylistSongBinding
    private var listSongs: MutableList<ItemSong>? = null
    private var isCheck: Boolean? = null
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
        isCheck = (activity as MainActivity).getCheckCurrentMedia()
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().linkSong.observe(this, Observer {
            binding.rc.adapter?.notifyDataSetChanged()
        })
    }

    fun resetPlaylist() {
        listSongs = (activity as MainActivity).getListSong()
        binding.rc.adapter!!.notifyDataSetChanged()
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

        (activity as MainActivity).initMedia(position, null, isCheck!!)
    }

    override fun setOnClickPopupMenu(position: Int, btn: Button) {
        val menu = PopupMenu(context, btn)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.down_load -> {
                    if (isCheck!!) {
                        val linkSong = listSongs!![position].linkSong
                        (activity as MainActivity).downLoadSong(linkSong)
                    } else {
                        Toast.makeText(
                            context,
                            "Bài hát đã được tải về",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.delete -> {
                    binding.rc.adapter!!.notifyItemRemoved(position)
                    listSongs!!.removeAt(position)
                    binding.rc.adapter!!.notifyItemChanged(position)

                }
            }
            true
        }
        menu.show()
    }
}