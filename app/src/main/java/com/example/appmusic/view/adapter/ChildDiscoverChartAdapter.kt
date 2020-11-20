package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemChartBinding
import com.example.appmusic.model.ItemSong

class ChildDiscoverChartAdapter : RecyclerView.Adapter<ChildDiscoverChartAdapter.ChartHolder> {

    var iChart: IChart

    constructor(iChart: IChart) {
        this.iChart = iChart
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartHolder {
        return ChartHolder(
            ItemChartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = iChart.getSizeChart()

    override fun onBindViewHolder(holder: ChartHolder, position: Int) {
        holder.binding.data = iChart.getListChart(position)
        holder.binding.root.setOnClickListener {
            iChart.setOnclickItemChart(position)
        }
        val btn = holder.binding.btnMenu
        btn.setOnClickListener {
            iChart.setOnClickPopupMenu(position, btn)
        }
    }

    class ChartHolder(val binding: ItemChartBinding) : RecyclerView.ViewHolder(binding.root)

    interface IChart {
        fun getSizeChart(): Int
        fun getListChart(position: Int): ItemSong
        fun setOnclickItemChart(position: Int)
        fun setOnClickPopupMenu(position: Int, btn: Button)
    }
}