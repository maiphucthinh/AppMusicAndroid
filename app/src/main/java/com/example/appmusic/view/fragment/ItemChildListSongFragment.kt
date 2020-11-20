package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.R
import com.example.appmusic.databinding.ItemChildListSongFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter

class ItemChildListSongFragment : Fragment(), ChildDiscoverChartAdapter.IChart,
    View.OnClickListener {
    private lateinit var binding: ItemChildListSongFragmentBinding
    private var parentPosition = 1
    private var listSongs = mutableListOf<ItemSong>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemChildListSongFragmentBinding.inflate(
            inflater, container, false
        )
        parentPosition = arguments?.getInt("POSITION", 1)!!
        register()
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter = ChildDiscoverChartAdapter(this)
        binding.btnRandom.setOnClickListener(this)
        val title = (activity as MainActivity).getModel().chart.value!![parentPosition].name
        binding.topic.text = title
        listSongs = (activity as MainActivity).getModel()
            .chart.value!![parentPosition].values
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().chart.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
            listSongs = (activity as MainActivity).getModel()
                .chart.value!![parentPosition].values
        })
    }

    override fun getSizeChart(): Int {
        if ((activity as MainActivity).getModel().chart.value == null) {
            return 0
        }
        return listSongs.size
    }

    override fun getListChart(position: Int): ItemSong {
        return listSongs[position]
    }

    override fun setOnclickItemChart(position: Int) {

        (activity as MainActivity).initMedia(position, listSongs)
    }

    override fun setOnClickPopupMenu(position: Int, btn: Button) {
        val menu = PopupMenu(context, btn)
        menu.menuInflater.inflate(R.menu.menu_itemsong_online, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.down_load -> {
                    (activity as MainActivity).downLoadSong(
                        listSongs[position].linkSong
                    )
                }
                R.id.delete -> {
                    binding.rc.adapter!!.notifyItemRemoved(position)
                    listSongs.removeAt(position)
                    binding.rc.adapter!!.notifyItemChanged(position)
                }
            }
            true
        }
        menu.show()
    }

    override fun onClick(v: View?) {
        (activity as MainActivity).initMedia(null, listSongs)
    }
}