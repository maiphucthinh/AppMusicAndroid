package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.ItemChildListSongFragmentBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.view.MainActivity
import com.example.appmusic.view.adapter.ChildDiscoverChartAdapter

class ItemChildListSongFragment : Fragment(), ChildDiscoverChartAdapter.IChart {
    private lateinit var binding: ItemChildListSongFragmentBinding
    private var parentPosition = 1
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
        val title = (activity as MainActivity).getModel().chart.value!![parentPosition].name
        binding.topic.setText(title)
        return binding.root
    }

    private fun register() {
        (activity as MainActivity).getModel().chart.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })
    }

    override fun getSizeChart(): Int {
        if ((activity as MainActivity).getModel().chart.value == null) {
            return 0
        }
        return (activity as MainActivity).getModel().chart.value!![parentPosition].values.size
    }

    override fun getListChart(position: Int): ItemSong {
        return (activity as MainActivity).getModel().chart.value!![parentPosition].values[position]
    }

    override fun setOnclickItemChart(position: Int) {
        (activity as MainActivity).getModel().linkSong(
            (activity as MainActivity).getModel()
                .chart.value!![parentPosition].values[position].linkSong
        )
        val listSongs = (activity as MainActivity).getModel()
            .chart.value!![parentPosition].values
        (activity as MainActivity).initMedia(position,listSongs)
    }
}