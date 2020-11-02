package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemChildVideoBinding
import com.thin.music.model.ItemSearchOnline

class ItemChildVideoAdapter : RecyclerView.Adapter<ItemChildVideoAdapter.VideoHolder> {
    var iVideo: IChildVideo

    constructor(iVidep: IChildVideo) {
        this.iVideo = iVidep
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            ItemChildVideoBinding.inflate(
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
        iVideo.setColor(position)?.let { holder.binding.bgr.setBackgroundColor(it) }
    }

    class VideoHolder(val binding: ItemChildVideoBinding) : RecyclerView.ViewHolder(binding.root)
    interface IChildVideo {
        fun getSizeVideo(): Int
        fun getListVideo(position: Int): ItemSearchOnline
        fun setOnClickItemVideo(position: Int)
        fun setColor(position: Int): Int?
    }
}