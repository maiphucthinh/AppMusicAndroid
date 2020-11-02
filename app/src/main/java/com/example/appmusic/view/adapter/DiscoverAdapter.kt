package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemDiscoverTypeTwoBinding
import com.example.appmusic.databinding.ItemListsSongBinding
import com.example.appmusic.model.ItemSong
import com.thin.music.model.ItemMusicList

class DiscoverAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var iDiscover: IDiscover


    constructor(iDiscover: IDiscover) {
        this.iDiscover = iDiscover
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0) {
            val holder = ItemListsSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            holder.rcChart.layoutManager = LinearLayoutManager(
                parent.context, RecyclerView.HORIZONTAL, false
            )
            return DiscoverHolder(holder)
        } else {
            val holder1 = ItemDiscoverTypeTwoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            holder1.rc.layoutManager = LinearLayoutManager(
                parent.context, RecyclerView.VERTICAL, false
            )
            return DiscoverHolder2(holder1)

        }


    }

    override fun getItemCount() = iDiscover.getSizeChart()

    override fun onBindViewHolder(holderParent: RecyclerView.ViewHolder, parentPosition: Int) {
        val data = iDiscover.getItemChart(parentPosition)
        if (getItemViewType(position = parentPosition) == 0) {
            val holder = holderParent as DiscoverHolder


            val iTopic = object : ChildDiscoverAdapter.ITopic {
                override fun getSize() = data.values.size

                override fun getData(childPosition: Int) = data.values[childPosition]

                override fun setOnClicItem(childPosition: Int) {
                    val linkTheme = data.values[childPosition].linkSong
                    iDiscover.setOnClickItem(parentPosition, childPosition, linkTheme)
                }
            }
            val itheme = object : ItemThemeAdapter.ITheme {
                override fun getSize(): Int {
                    if (parentPosition == 3) {
                        return 8
                    }
                    return data.values.size
                }

                override fun getDataTheme(position: Int) = data.values[position]

                override fun setOnClickItem(position: Int) {
                    val linkTheme = data.values[position].linkSong
                    iDiscover.setOnClickItem(parentPosition, position, linkTheme)
                }

            }
            holder.binding.topic.setText(data.name)
            if (parentPosition == 2) {

                if (holder.binding.rcChart.adapter == null) {
                    holder.binding.rcChart.adapter = ChildDiscoverAdapter(iTopic)
                } else {
                    (holder.binding.rcChart.adapter as ChildDiscoverAdapter).iTopic = iTopic
                    holder.binding.rcChart.adapter!!.notifyDataSetChanged()
                }
            } else {
                if (holder.binding.rcChart.adapter == null) {
                    holder.binding.rcChart.adapter = ItemThemeAdapter(itheme)
                } else {
                    (holder.binding.rcChart.adapter as ItemThemeAdapter).iTheme = itheme
                    holder.binding.rcChart.adapter!!.notifyDataSetChanged()
                }

            }
        } else {
            val holder = holderParent as DiscoverHolder2
            val iChart = object : ChildDiscoverChartAdapter.IChart {
                override fun getSizeChart() = 6

                override fun getListChart(position: Int) = data.values[position]

                override fun setOnclickItemChart(position: Int) {
                    val linkTheme = data.values[position].linkSong
                    val songName = data.values[position].songName
                    val artistName = data.values[position].artistName
                    val linkImage = data.values[position].linkImage
                    iDiscover.setItemSongTypeTwo(
                        position,
                        songName,
                        artistName,
                        linkTheme,
                        linkImage,
                        data.values
                    )
                    iDiscover.setOnClickItem(parentPosition, position, linkTheme)
                }
            }
            holder.binding.more.setOnClickListener {
                iDiscover.setOnClickMore(parentPosition)
            }
            holder.binding.topic.setText(data.name)

            if (holder.binding.rc.adapter == null) {
                holder.binding.rc.adapter = ChildDiscoverChartAdapter(iChart)
            } else {
                (holder.binding.rc.adapter as ChildDiscoverChartAdapter).iChart = iChart
                holder.binding.rc.adapter!!.notifyDataSetChanged()
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            1 -> return 1
            4 -> return 1
        }
        return 0
    }


    class DiscoverHolder(val binding: ItemListsSongBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiscoverHolder2(val binding: ItemDiscoverTypeTwoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface IDiscover {
        fun getSizeChart(): Int
        fun getItemChart(position: Int): ItemMusicList<ItemSong>
        fun setOnClickItem(parentPosition: Int, position: Int, linkTheme: String?)
        fun setOnClickMore(parentPosition: Int)
        fun setItemSongTypeTwo(
            position: Int,
            songName: String?,
            artistName: String?,
            linkSong: String?,
            linkImage: String?,
            listSong: MutableList<ItemSong>?
        )
    }
}