package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSearchPlayListBinding
import com.example.appmusic.model.ItemSong

class ItemSearchPlayListAdapter : RecyclerView.Adapter<ItemSearchPlayListAdapter.PlayListHolder> {
    var iPlayList: IPlayList

    constructor(iPlayList: IPlayList) {
        this.iPlayList = iPlayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListHolder {
        return PlayListHolder(
            ItemSearchPlayListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iPlayList.getSizePlayList()

    override fun onBindViewHolder(holder: PlayListHolder, position: Int) {
        holder.binding.data = iPlayList.getListPlayList(position)
        holder.binding.root.setOnClickListener {
            iPlayList.setOnClickItemPlayList(position)
        }

}

    class PlayListHolder(val binding: ItemSearchPlayListBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface IPlayList {
        fun getSizePlayList(): Int
        fun getListPlayList(position: Int): ItemSong
        fun setOnClickItemPlayList(position: Int)
    }
}