package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemVideoOnlineBinding
import com.example.appmusic.model.ItemSong

class ItemVideoAdapter:RecyclerView.Adapter<ItemVideoAdapter.VideoHolder> {
    var iVideo: IVideo

    constructor(iVidep: IVideo) {
        this.iVideo = iVidep
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            ItemVideoOnlineBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iVideo.getSizeVideo()

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.binding.data = iVideo.getListVideo(position)
        holder.binding.root.setOnClickListener {
            iVideo.setOnClickItemVideo(position)
        }
    }

    class VideoHolder(val binding: ItemVideoOnlineBinding) : RecyclerView.ViewHolder(binding.root)
    interface IVideo {
        fun getSizeVideo(): Int
        fun getListVideo(position: Int): ItemSong
        fun setOnClickItemVideo(position: Int)
    }
}