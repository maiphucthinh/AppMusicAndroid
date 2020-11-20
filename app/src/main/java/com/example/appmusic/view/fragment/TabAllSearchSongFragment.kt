package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.R
import com.example.appmusic.databinding.AllSearchFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.AllSongSearchAdapter
import com.example.appmusic.view.viewpager.ParentSearchSong
import com.thin.music.model.ItemMusicList


class TabAllSearchSongFragment : Fragment(), AllSongSearchAdapter.IAllSearch {
    private lateinit var binding: AllSearchFragmentBinding
    private var isCheckLoading = true
    private var parentSearchSong: ParentSearchSong? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllSearchFragmentBinding.inflate(
            inflater, container, false
        )
        (activity as MainActivity).getModel().getVideo()
        register()
        binding.rcAll.layoutManager = LinearLayoutManager(context)
        binding.rcAll.adapter = AllSongSearchAdapter(this)
        parentSearchSong = (activity as MainActivity).getParenFragmentSearch()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSearchAll.observe(this, Observer {
            binding.rcAll.adapter!!.notifyDataSetChanged()
        })
        (activity as MainActivity).getModel().isRuningSearch.observe(this, Observer {
            isCheckLoading = it

            if (isCheckLoading) {
                binding.prg.visibility = View.VISIBLE
            } else
                binding.prg.visibility = View.GONE
        })
    }

    override fun getSizeChart(): Int {
        if ((activity as MainActivity).getModel().listSearchAll.value == null) {
            return 0
        }
        if ((activity as MainActivity).getModel().listSearchAll.value!![0].values.size == 0) {
            return 0
        }
        return (activity as MainActivity).getModel().listSearchAll.value!!.size
    }

    override fun getItemChart(position: Int): ItemMusicList<ItemSong> {

        return (activity as MainActivity).getModel().listSearchAll.value!![position]
    }

    override fun setOnClickItem(
        parentPosition: Int,
        position: Int,
        linkTheme: String?,
        linkArtist: String?,
        listSong: MutableList<ItemSong>,
        linkSong: String?
    ) {
        when (parentPosition) {
            0 -> {
                (activity as MainActivity).initMedia(position, listSong)
            }
            1 -> {
                (activity as MainActivity).getModel().getChildLinkTheme(linkTheme)
                (activity as MainActivity).openChildListTheme()
            }
            2 -> {
                (activity as MainActivity).getModel().getLinkVideo(linkSong)
                (activity as MainActivity).openVideo(position)
            }
            3 -> {
                (activity as MainActivity).openParentPageArtist()
                (activity as MainActivity).getModel().getAllArtistSong(linkArtist)
            }
        }

    }

    override fun setOnclickMore(parentPosition: Int) {

        parentSearchSong?.setCLickNext(parentPosition + 1)
    }


    override fun setOnclickPopupMenu(
        parentPosition: Int,
        position: Int,
        btnMenu: Button,
        rc: RecyclerView,
        linkSong: String?
    ) {
        val menu = PopupMenu(context, btnMenu)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.down_load -> {
                    when (parentPosition) {
                        0 -> (activity as MainActivity).downLoadSong(linkSong!!)
                        1 -> {
                            Toast.makeText(
                                context, "Không thể tải playlist",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        2 -> {
                            Toast.makeText(
                                context, "Không thể tải MV",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        3 -> {
                            Toast.makeText(
                                context, "Không thể tải Nghệ sĩ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
                R.id.delete -> {
                    rc.adapter!!.notifyItemRemoved(position)
                    rc.adapter!!.notifyItemChanged(position)
                }
            }
            true
        }
        menu.show()
    }
}