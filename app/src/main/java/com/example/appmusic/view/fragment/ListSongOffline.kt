package com.example.appmusic.view.fragment

import android.annotation.SuppressLint
import android.content.ContentUris
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.appmusic.databinding.FragmentListSongsOfflineBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ItemSongSearchAdapter
import java.io.File

class ListSongOffline : Fragment(), ItemSongSearchAdapter.ISongOnline, View.OnClickListener {
    private lateinit var binding: FragmentListSongsOfflineBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListSongsOfflineBinding.inflate(
            inflater, container, false
        )
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter = ItemSongSearchAdapter(this)
        binding.tvTotalSongs.text = "Tất cả (" +
                (activity as MainActivity).getModel().lisSongOffline.value!!.size + ")"
        register()
        binding.btnRandomSongs.setOnClickListener(this)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun register() {
        (activity as MainActivity).getModel().lisSongOffline.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
            binding.tvTotalSongs.text = "Tất cả (" +
                    (activity as MainActivity).getModel().lisSongOffline.value!!.size + ")"
        })
    }

    override fun getSize(): Int {
        val model = (activity as MainActivity).getModel()
        if (model.lisSongOffline.value == null) {
            return 0
        }
        return model.lisSongOffline.value!!.size
    }

    override fun getSongOnline(position: Int): ItemSong? {
        return (activity as MainActivity).getModel().lisSongOffline.value!![position]
    }

    override fun setOnClickItem(position: Int) {
        val list = (activity as MainActivity).getModel().lisSongOffline.value!!
        (activity as MainActivity).initMedia(
            position, list, false
        )
    }


    override fun setOnClickMenu(position: Int, btnMenu: Button) {
        val menu = PopupMenu(context, btnMenu)
        menu.menuInflater.inflate(R.menu.menu_itemsong_delete, menu.menu)
        menu.setOnMenuItemClickListener{
            val list = (activity as MainActivity).getModel().lisSongOffline.value!!
            val contentUri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                (list[position].id).toLong()
            )
            val file = File(list[position].linkSong)
            val deleted = file.delete()
            if (deleted) {
                context!!.contentResolver.delete(contentUri, null, null)
                binding.rc.adapter!!.notifyItemRemoved(position)
                binding.rc.adapter!!.notifyItemChanged(position)
                (activity as MainActivity).getModel().loadAllMusicOffline(context!!)
                Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, "Xoá Không thành công", Toast.LENGTH_SHORT).show()
             true
        }

        menu.show()
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).initMedia(
            null,
            (activity as MainActivity).getModel().lisSongOffline.value!!, false
        )
    }
}