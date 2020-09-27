package com.example.appmusic.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusic.databinding.DiscoverPageBinding
import com.example.appmusic.model.ItemSong
import com.example.appmusic.model.ItemTopic
import com.example.appmusic.view.MainFragment
import com.example.appmusic.view.adapter.DiscoverAdapter
import com.example.appmusic.view.adapter.ChildDiscoverAdapter
import com.thin.music.model.ItemMusicList

class DiscoverFragment : Fragment(),  DiscoverAdapter.IDiscover {

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

        binding.rc.layoutManager = LinearLayoutManager(context)
        binding.rc.adapter =
            DiscoverAdapter(this)
        register()
        initListTopic()
        return binding.root
    }

    private fun register() {
        data.getModel().chart.observe(this, Observer {
            binding.rc.adapter!!.notifyDataSetChanged()
        })
    }

    private fun initListTopic() {
//        listOfTopics.add(ItemTopic("Chart  >", DiscoverAdapter(this)))
//        listOfTopics.add(ItemTopic("Chart  >", DiscoverAdapter(this)))

        data.getModel().getChart()

    }

    override fun getSizeChart(): Int {
        if (data.getModel().chart.value == null){
            return 0
        }
        return data.getModel().chart.value!!.size
    }

    override fun getItemChart(position: Int): ItemMusicList<ItemSong> {
        return  data.getModel().chart.value!![position]
    }






    override fun setOnClickItem(position: Int) {

    }
}