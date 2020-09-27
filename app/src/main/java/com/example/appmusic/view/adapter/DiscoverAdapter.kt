package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemListsSongBinding
import com.example.appmusic.model.ItemSong
import com.thin.music.model.ItemMusicList

class DiscoverAdapter : RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder> {
    var iDiscover: IDiscover

    constructor(iDiscover: IDiscover) {
        this.iDiscover = iDiscover
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverHolder {
        val holder= DiscoverHolder(
            ItemListsSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.binding.rcChart.layoutManager = LinearLayoutManager(
            parent.context, RecyclerView.HORIZONTAL, false
        )
        return holder
    }

    override fun getItemCount() = iDiscover.getSizeChart()

    override fun onBindViewHolder(holder: DiscoverHolder, parentPosition: Int) {
        val data = iDiscover.getItemChart(parentPosition)
        val iTopic = object :ChildDiscoverAdapter.ITopic{
            override fun getSize()=data.values.size

            override fun getData(childPosition: Int)= data.values[childPosition]
        }
        if (holder.binding.rcChart.adapter == null){
            holder.binding.rcChart.adapter = ChildDiscoverAdapter(iTopic)
        }else {
            (holder.binding.rcChart.adapter as ChildDiscoverAdapter).iTopic=iTopic
            holder.binding.rcChart.adapter!!.notifyDataSetChanged()
        }

    }

    override fun getItemId(position: Int): Long {
        if (position == 0){
            return 0
        }
        return 20
    }

    class DiscoverHolder(val binding: ItemListsSongBinding) : RecyclerView.ViewHolder(binding.root)

    interface IDiscover {
        fun getSizeChart(): Int
        fun getItemChart(position: Int):ItemMusicList<ItemSong>
        fun setOnClickItem(position: Int)
    }
}