package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.R
import com.example.appmusic.databinding.TabSearchPlayListFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter

class SongTabArtistFragment : Fragment(), ChildDiscoverChartAdapter.IChart {
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
        rehister()
        binding.rcAlbums.layoutManager = LinearLayoutManager(context)
        binding.rcAlbums.adapter = ChildDiscoverChartAdapter(this)
        return binding.root
    }

    private fun rehister() {
        (activity as MainActivity).getModel().listArtistSong.observe(this, Observer {
            binding.rcAlbums.adapter!!.notifyDataSetChanged()
        })
        (activity as MainActivity).getModel().isLoading.observe(this, Observer {
            isCheckLoading = it
            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            }
            binding.prg.visibility = View.GONE
        })

    }

    override fun getSizeChart(): Int {
        if ((activity as MainActivity).getModel().listArtistSong.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().listArtistSong.value!![0].values.size
    }

    override fun getListChart(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listArtistSong.value!![0].values[position]
    }

    override fun setOnclickItemChart(position: Int) {
        (activity as MainActivity).initMedia(
            position,
            (activity as MainActivity).getModel().listArtistSong.value!![0].values
        )
    }

    override fun setOnClickPopupMenu(position: Int, btn: Button) {
        val menu = PopupMenu(context, btn)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.down_load -> {
                    val linkSong =
                        (activity as MainActivity).getModel()
                            .listArtistSong.value!![0].values[position].linkSong
                    (activity as MainActivity).downLoadSong(linkSong)
                }
                R.id.delete -> {
                    binding.rcAlbums.adapter!!.notifyItemRemoved(position)
                    (activity as MainActivity)
                        .getModel().listArtistSong.value!![0]
                        .values.removeAt(position)
                    binding.rcAlbums.adapter!!.notifyItemChanged(position)
                }
            }
            true
        }
        menu.show()
    }
}