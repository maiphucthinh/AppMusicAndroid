package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemInfomationVideoBinding
import com.thin.music.model.GetLinkMusic

class InfomationVideoAdapter : RecyclerView.Adapter<InfomationVideoAdapter.InfoHolder> {
    var info: IInfomation

    constructor(info: IInfomation) {
        this.info = info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoHolder {
        return InfoHolder(
            ItemInfomationVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = info.getSizeInfo()

    override fun onBindViewHolder(holder: InfoHolder, position: Int) {
        holder.binding.data = info.getDataInfo(position)
    }

    class InfoHolder(val binding: ItemInfomationVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface IInfomation {
        fun getSizeInfo(): Int
        fun getDataInfo(position: Int): GetLinkMusic
    }
}