package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.DiscoverPageBinding
import com.example.appmusic.model.ItemChart
import com.example.appmusic.model.ItemSong
import com.example.appmusic.model.ItemTopic
import com.example.appmusic.view.MainFragment
import com.example.appmusic.view.adapter.ChartAdapter
import com.example.appmusic.view.adapter.ListTopicsAdapter

class DiscoverFragment : Fragment(), ListTopicsAdapter.ITopic, ChartAdapter.IChart {
    private val listOfTopics = mutableListOf<ItemTopic>()
//    private val chart = mutableListOf<ItemChart>()
//    private val song = mutableListOf<ItemSong>()

    private lateinit var binding: DiscoverPageBinding
    private lateinit var data: MainFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DiscoverPageBinding.inflate(
            inflater, container, false
        )
        data = (activity as MainFragment)
        data.getModel().getChart()
        initListTopic()
        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter =
            ChartAdapter(this)
        register()
        return binding.root
    }

    private fun register() {
        data.getModel().chart.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })
    }

    private fun initListTopic() {
        listOfTopics.add(ItemTopic("Chart  >", ChartAdapter(this)))
        listOfTopics.add(ItemTopic("Chart  >", ChartAdapter(this)))



    }

    override fun getSize() = listOfTopics.size

    override fun getData(position: Int): ItemTopic {
        return listOfTopics.get(position)
    }

    override fun getSizeChart(): Int {
        if (data.getModel().chart.value == null) {
            return 0
        }
//        return data.getModel().chart.value!!.size
        return 20
    }

    override fun getItemChart(position: Int): ItemChart {
        return data.getModel().chart.value!![position]
    }

    override fun setOnClickItem(position: Int) {
        TODO("Not yet implemented")
    }
}