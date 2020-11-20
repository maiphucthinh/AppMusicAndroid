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
import com.example.appmusic.R
import com.example.appmusic.databinding.SearchSongBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.Utils
import com.example.appmusic.view.adapter.ItemSongSearchAdapter


class TabSearchSongFragment : Fragment(), ItemSongSearchAdapter.ISongOnline,
    PopupMenu.OnMenuItemClickListener {
    private lateinit var data: MainActivity
    private lateinit var binding: SearchSongBinding
    private var index = 0
    private var parentIndex = 0
    private var isCheckLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SearchSongBinding.inflate(
            inflater, container, false
        )
        data = (activity as MainActivity)
        binding.data = data.getModel()
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter =
            ItemSongSearchAdapter(this)
        register()
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().listSearchAll.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })

        data.getModel().isRuningSearch.observe(this, Observer {
            isCheckLoading = it
        })

        data.getModel().songData.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })

    }

    override fun getSize(): Int {
        val list = (activity as MainActivity).getModel().listSearchAll.value
        if (list == null) {
            return 0
        }
        return list!![parentIndex].values.size
    }

    override fun getSongOnline(position: Int): ItemSong {
        return (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values[position]
    }

    override fun setOnClickItem(position: Int) {
        (activity as MainActivity).initMedia(
            position,
            (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values
        )
    }

    override fun setOnClickMenu(position: Int, btnMenu: Button) {
        index = position
        val menu = PopupMenu(context, btnMenu)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener(this)
        menu.show()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.down_load ->{
                val linkHtml =
                    (activity as MainActivity).getModel().listSearchAll.value!![parentIndex].values[index].linkSong!!
                (activity as MainActivity).downLoadSong(linkHtml)
            }
            R.id.delete->{
                binding.rc.adapter!!.notifyItemRemoved(index)
                binding.rc.adapter!!.notifyItemChanged(index)
            }
        }
        return false
    }
}