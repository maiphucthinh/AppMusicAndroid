package com.example.appmusic.view.fragment

import android.annotation.SuppressLint
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
import com.example.appmusic.databinding.ListThemeBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.SongOnlineAdapter

class ChildListThemeFragment : Fragment(), SongOnlineAdapter.ISongOnline, View.OnClickListener {
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
        binding.btnRandomSongs.setOnClickListener(this)
        (activity as MainActivity).addContentChild()
        register()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun register() {
        (activity as MainActivity).getModel().listThemeSearch.observe(this, Observer {
            binding.rcTheme.adapter!!.notifyDataSetChanged()
            val totalSong = (activity as MainActivity).getModel().listThemeSearch.value!!.size
            binding.totalList.text = "$totalSong bài hát"
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
        (activity as MainActivity).initMedia(
            position,
            (activity as MainActivity).getModel().listThemeSearch.value!!
        )
    }

    override fun setOnClickMenu(position: Int, btnMenu: Button) {
        val menu = PopupMenu(context, btnMenu)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.down_load -> {
                    val linkSong =
                        (activity as MainActivity).getModel()
                            .listThemeSearch.value!![position].linkSong
                    (activity as MainActivity).downLoadSong(linkSong)
                }
                R.id.delete -> {
                    binding.rcTheme.adapter!!.notifyItemRemoved(position)
                    (activity as MainActivity).getModel().listThemeSearch.value!!.removeAt(position)
                    binding.rcTheme.adapter!!.notifyItemChanged(position)
                }
            }
            true
        }
        menu.show()
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).initMedia(
            null,
            (activity as MainActivity).getModel().listThemeSearch.value!!
        )
    }
}