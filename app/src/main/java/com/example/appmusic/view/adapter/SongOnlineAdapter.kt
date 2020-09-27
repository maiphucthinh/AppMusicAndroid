package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSongBinding
import com.example.appmusic.model.ItemSong

class SongOnlineAdapter : RecyclerView.Adapter<SongOnlineAdapter.SongHolder> {

    private var iSong: ISongOnline

    constructor(iSong: ISongOnline) {
        this.iSong = iSong
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        return SongHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = iSong.getSize()

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.binding.data = iSong.getSongOnline(position)
        holder.binding.root.setOnClickListener {
            iSong.setOnClickItem(position)
        }
        holder.binding.btnMenu.setOnClickListener {
            iSong.setOnClickMenu(position,holder.binding.btnMenu)
        }
    }

    class SongHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    interface ISongOnline {
        fun getSize(): Int
        fun getSongOnline(position: Int): ItemSong
        fun setOnClickItem(position: Int)
        fun setOnClickMenu(position: Int, btnMenu: Button)
    }
}