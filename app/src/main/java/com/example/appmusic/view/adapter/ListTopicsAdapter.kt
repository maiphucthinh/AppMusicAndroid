package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemListsSongBinding
import com.example.appmusic.model.ItemChart
import com.example.appmusic.model.ItemTopic

class ListTopicsAdapter : RecyclerView.Adapter<ListTopicsAdapter.TopicHolder> {

    var iTopic: ITopic

    constructor(iTopic: ITopic) {
        this.iTopic = iTopic
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val holder =  TopicHolder(
            ItemListsSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.binding.rcChart.layoutManager = LinearLayoutManager(
            holder.binding.rcChart.context, RecyclerView.HORIZONTAL, false)
        return holder
    }

    override fun getItemCount() = iTopic.getSize()

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val data = iTopic.getData(position)
//test
        holder.binding.data = data

        val iChart = object :ChartAdapter.IChart{
            override fun getSizeChart(): Int {
                data.
            }

            override fun getItemChart(position: Int): ItemChart {

            }

            override fun setOnClickItem(position: Int) {

            }
        }

        val adapter:ChartAdapter
        if (holder.binding.rcChart.tag == null ){
            adapter = ChartAdapter(iChart)
            holder.binding.rcChart.adapter = adapter
        }else {
            adapter = holder.binding.rcChart.tag as ChartAdapter
            adapter.iChart = iChart
            adapter.notifyDataSetChanged()
        }

    }

    class TopicHolder(val binding: ItemListsSongBinding) : RecyclerView.ViewHolder(binding.root)

    interface ITopic {
        fun getSize(): Int
        fun getData(position: Int): ItemTopic
    }

}