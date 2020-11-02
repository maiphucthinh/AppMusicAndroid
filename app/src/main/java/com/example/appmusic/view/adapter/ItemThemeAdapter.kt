package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemThemeBinding
import com.example.appmusic.model.ItemSong

class ItemThemeAdapter : RecyclerView.Adapter<ItemThemeAdapter.ThemeHolder> {
    var iTheme: ITheme

    constructor(iTheme: ITheme) {
        this.iTheme = iTheme
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        return ThemeHolder(
            ItemThemeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iTheme.getSize()

    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        holder.binding.data = iTheme.getDataTheme(position)
        holder.binding.root.setOnClickListener {
            iTheme.setOnClickItem(position)
        }
    }

    class ThemeHolder(val binding: ItemThemeBinding) : RecyclerView.ViewHolder(binding.root)
    interface ITheme {
        fun getSize(): Int
        fun getDataTheme(position: Int): ItemSong
        fun setOnClickItem(position: Int)
    }
}