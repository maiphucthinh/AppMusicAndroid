package com.example.appmusic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusic.databinding.ItemChartBinding
import com.example.appmusic.model.ItemChart

class ChartAdapter : RecyclerView.Adapter<ChartAdapter.ChartHolder> {
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
        holder.binding.data = iChart.getItemChart(position)
        holder.binding.root.setOnClickListener {
            iChart.setOnClickItem(position)
        }
    }

    class ChartHolder(val binding: ItemChartBinding) : RecyclerView.ViewHolder(binding.root)

    interface IChart {
        fun getSizeChart(): Int
        fun getItemChart(position: Int): ItemChart
        fun setOnClickItem(position: Int)
    }
}