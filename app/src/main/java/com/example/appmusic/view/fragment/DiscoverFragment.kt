package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.DiscoverPageBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.DiscoverAdapter
import com.thin.music.model.ItemMusicList

class DiscoverFragment : Fragment(), DiscoverAdapter.IDiscover {

    private lateinit var binding: DiscoverPageBinding
    private lateinit var data: MainActivity
    private var isCheckLoading = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverPageBinding.inflate(
            inflater, container, false
        )
        data = (activity as MainActivity)
        data.getModel().getChart()

        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter =
            DiscoverAdapter(this)
        register()
        return binding.root
    }

    private fun register() {
        data.getModel().chart.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })

        (activity as MainActivity).getModel().isLoading.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            } else
                binding.prg.visibility = View.GONE
        })
    }

    override fun getSizeChart(): Int {
        if (data.getModel().chart.value == null) {
            return 0
        }
        return data.getModel().chart.value!!.size
    }

    override fun getItemChart(position: Int): ItemMusicList<ItemSong> {
        return data.getModel().chart.value!![position]
    }

    override fun setOnClickMore(parentPosition: Int) {
        (activity as MainActivity)
            .openItemChildListSongFragment(parentPosition)
    }

    override fun setItemSongTypeTwo(
        position: Int,
        songName: String?,
        artistName: String?,
        linkSong: String?,
        linkImage: String?,
        listSong: MutableList<ItemSong>?
    ) {
        (activity as MainActivity).getModel().linkSong(linkSong)
        if (listSong != null) {
            (activity as MainActivity).initMedia(
                position,
                listSong
            )
        }
    }

    override fun setOnClickItem(parentPosition: Int, position: Int, linkTheme: String?) {
        when (parentPosition) {
            0 -> {
                (activity as MainActivity).getModel().getLinkTheme(linkTheme)
                (activity as MainActivity).openListTheme()
            }
            2, 3, 6 -> {
                (activity as MainActivity).openChildListTheme()
                (activity as MainActivity).getModel().getChildLinkTheme(linkTheme)
            }
            5 -> {
                (activity as MainActivity).openParentPageArtist()
                (activity as MainActivity).getModel().getAllArtistSong(linkTheme)
            }
        }
    }
}