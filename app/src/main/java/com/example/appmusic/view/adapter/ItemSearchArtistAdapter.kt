package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSearchArtistBinding
import com.example.appmusic.model.ItemSong

class ItemSearchArtistAdapter : RecyclerView.Adapter<ItemSearchArtistAdapter.ArtistHolder> {
    var iArtist: IChildArtist

    constructor(iArtist: IChildArtist) {
        this.iArtist = iArtist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        return ArtistHolder(
            ItemSearchArtistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iArtist.getSizeArtist()

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        holder.binding.data = iArtist.getListArtist(position)
        holder.binding.root.setOnClickListener {
            iArtist.setOnClickItemArtist(position)
        }
    }

    class ArtistHolder(val binding: ItemSearchArtistBinding) : RecyclerView.ViewHolder(binding.root)
    interface IChildArtist {
        fun getSizeArtist(): Int
        fun getListArtist(position: Int): ItemSong
        fun setOnClickItemArtist(position: Int)
    }
}