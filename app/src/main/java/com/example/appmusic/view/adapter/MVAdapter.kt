package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemVideoBinding
import com.thin.music.model.ItemSearchOnline

class MVAdapter : RecyclerView.Adapter<MVAdapter.VideoHolder> {
    var iVidep: IVideo

    constructor(iVidep: IVideo) {
        this.iVidep = iVidep
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iVidep.getSizeVideo()

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.binding.data = iVidep.getListVideo(position)
        holder.binding.root.setOnClickListener {
            iVidep.setOnClickItemVideo(position)
        }
    }

    class VideoHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)
    interface IVideo {
        fun getSizeVideo(): Int
        fun getListVideo(position: Int): ItemSearchOnline
        fun setOnClickItemVideo(position: Int)
    }
}