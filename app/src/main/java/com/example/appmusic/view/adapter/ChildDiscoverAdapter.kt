package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSquareSongBinding
import com.example.appmusic.model.ItemSong

class ChildDiscoverAdapter : RecyclerView.Adapter<ChildDiscoverAdapter.ChildHolder> {

    var iTopic: ITopic

    constructor(iTopic: ITopic) {
        this.iTopic = iTopic
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildHolder {
        val holder = ChildHolder(
            ItemSquareSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        return holder
    }

    override fun getItemCount() = iTopic.getSize()

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val data = iTopic.getData(position)
        holder.binding.data = data
        holder.binding.root.setOnClickListener {
            iTopic.setOnClickItem(position)
        }
    }

    class ChildHolder(val binding: ItemSquareSongBinding) : RecyclerView.ViewHolder(binding.root)

    interface ITopic {
        fun getSize(): Int
        fun getData(position: Int): ItemSong
        fun setOnClickItem(position: Int)
    }

}