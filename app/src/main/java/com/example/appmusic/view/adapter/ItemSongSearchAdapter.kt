package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSongSearchBinding
import com.example.appmusic.model.ItemSong

class ItemSongSearchAdapter : RecyclerView.Adapter<ItemSongSearchAdapter.SongOnlineHolder> {
    var iSong: ISongOnline

    constructor(iSong: ISongOnline) {
        this.iSong = iSong
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongOnlineHolder {
        return SongOnlineHolder(
            ItemSongSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = iSong.getSize()

    override fun onBindViewHolder(holder: SongOnlineHolder, position: Int) {
        if (iSong.getSongOnline(position) == null) {
            return
        }
            holder.binding.data = iSong.getSongOnline(position)
            holder.binding.root.setOnClickListener {
                iSong.setOnClickItem(position)
            }
            holder.binding.btnMenu.setOnClickListener {
                iSong.setOnClickMenu(position, holder.binding.btnMenu)
            }
    }

    class SongOnlineHolder(val binding: ItemSongSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ISongOnline {
        fun getSize(): Int
        fun getSongOnline(position: Int): ItemSong?
        fun setOnClickItem(position: Int)
        fun setOnClickMenu(position: Int, btnMenu: Button)
    }
}