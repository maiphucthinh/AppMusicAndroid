package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemSearchAllBinding
import com.example.appmusic.model.ItemSong
import com.thin.music.model.ItemMusicList

class AllSongSearchAdapter : RecyclerView.Adapter<AllSongSearchAdapter.AllSearchHolder> {
    var iAllSearch: IAllSearch


    constructor(iAllSearch: IAllSearch) {
        this.iAllSearch = iAllSearch
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSearchHolder {


        val holder = ItemSearchAllBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        holder.rcItem.layoutManager = LinearLayoutManager(
            parent.context, RecyclerView.VERTICAL, false
        )
        return AllSearchHolder(holder)


    }

    override fun getItemCount() = iAllSearch.getSizeChart()

    override fun onBindViewHolder(holder: AllSearchHolder, parentPosition: Int) {
        val data = iAllSearch.getItemChart(parentPosition)

        val iTopic = object : ItemSongSearchAdapter.ISongOnline {
            override fun getSize(): Int {
                if (data.values.size == 0) {
                    return 0
                }
                return 4
            }

            override fun getSongOnline(position: Int): ItemSong? {
                return data.values[position]
            }

            override fun setOnClickItem(position: Int) {
                val artist = data.values[position].linkSinger
                val linkTheme = data.values[position].linkAlbum
                val linkSong = data.values[position].linkSong
                iAllSearch.setOnClickItem(
                    parentPosition,
                    position,
                    linkTheme,
                    artist,
                    data.values,
                    linkSong
                )

            }

            override fun setOnClickMenu(position: Int, btnMenu: Button) {
                var linkSong: String? = null
                if (parentPosition == 0) {
                    linkSong = data.values[position].linkSong
                }
                val rc = holder.binding.rcItem
                iAllSearch.setOnclickPopupMenu(parentPosition, position, btnMenu, rc, linkSong)
            }

        }
        holder.binding.btnMore.setOnClickListener {
            iAllSearch.setOnclickMore(parentPosition)
        }
        holder.binding.nameList.text = data.name
        if (holder.binding.rcItem.adapter == null) {
            holder.binding.rcItem.adapter = ItemSongSearchAdapter(iTopic)
        } else {
            (holder.binding.rcItem.adapter as ItemSongSearchAdapter).iSong = iTopic
            holder.binding.rcItem.adapter!!.notifyDataSetChanged()
        }
    }


    class AllSearchHolder(val binding: ItemSearchAllBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface IAllSearch {
        fun getSizeChart(): Int
        fun getItemChart(position: Int): ItemMusicList<ItemSong>
        fun setOnClickItem(
            parentPosition: Int,
            position: Int,
            linkTheme: String?,
            linkArtist: String?,
            listSong: MutableList<ItemSong>,
            linkSong: String?
        )

        fun setOnclickMore(parentPosition: Int)
        fun setOnclickPopupMenu(
            parentPosition: Int,
            position: Int,
            btnMenu: Button,
            rc: RecyclerView,
            linkSong: String?

        )
    }
}