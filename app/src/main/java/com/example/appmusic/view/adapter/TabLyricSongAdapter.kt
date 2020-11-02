package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemLyricSongBinding
import com.thin.music.model.GetLinkMusic

class TabLyricSongAdapter : RecyclerView.Adapter<TabLyricSongAdapter.LyricHolder> {
    var iLyric: ILyric

    constructor(iLyric: ILyric) {
        this.iLyric = iLyric
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricHolder {
        return LyricHolder(
            ItemLyricSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iLyric.getSizeLyric()

    override fun onBindViewHolder(holder: LyricHolder, position: Int) {
        holder.binding.data = iLyric.getDataLyric()
    }

    class LyricHolder(val binding: ItemLyricSongBinding) : RecyclerView.ViewHolder(binding.root)
    interface ILyric {
        fun getSizeLyric(): Int
        fun getDataLyric(): GetLinkMusic
    }
}