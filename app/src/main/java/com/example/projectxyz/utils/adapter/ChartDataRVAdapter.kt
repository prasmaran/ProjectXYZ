@file:Suppress("IllegalIdentifier")

package com.example.projectxyz.utils.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectxyz.R
import com.example.projectxyz.databinding.ChartDataRowItemBinding
import com.example.projectxyz.model.meanValue.MeanValueChartData

class ChartDataRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chartDataList = mutableListOf<MeanValueChartData?>()

    inner class ChartDataItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chart_data_row_item, parent, false)
    ) {

        private val binding = ChartDataRowItemBinding.bind(itemView)

        fun onBind(chartDataItem: MeanValueChartData?) {

            binding.dataName = chartDataItem?.dataTestName.toString()
            binding.xAxis = chartDataItem?.x_axis.toString()
            binding.yAxis = chartDataItem?.y_axis.toString()
            binding.zAxis = chartDataItem?.z_axis.toString()
            binding.meanMmg = "= ${chartDataItem?.meanValue.toString()}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChartDataItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChartDataItemViewHolder).onBind(chartDataList[position]!!)
    }

    override fun getItemCount(): Int {
        return chartDataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(chartDataListItems: List<MeanValueChartData>) {
        this.chartDataList.clear()
        this.chartDataList.addAll(chartDataListItems)
        notifyDataSetChanged()
    }

}
