package com.example.projectxyz.utils.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectxyz.R
import com.example.projectxyz.databinding.ChartDataRowItemBinding
import com.example.projectxyz.model.user_list.DataMeasured
import java.util.*
import kotlin.collections.ArrayList

class ChartDataRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chartDataList = mutableListOf<DataMeasured?>()
    private var dataNumber = 0
    private val dataReference : ArrayList<String> = arrayListOf(
        "Baseline(Pre test)",
        "After knee extension with resistance (3sets)",
        "Baseline (Post knee extension test)",
        "After squat without load",
        "After squat with load(1RM)",
        "After squat with load(2RM)",
        "After squat with load(3RM)",
        "Baseline (Post Squat test)"
    )

    inner class ChartDataItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chart_data_row_item, parent, false)
    ) {

        private val binding = ChartDataRowItemBinding.bind(itemView)

        fun onBind(chartDataItem: DataMeasured?) {

            binding.dataName = dataReference[dataNumber]
            binding.xAxis = chartDataItem?.x_axis.toString()
            binding.yAxis = chartDataItem?.y_axis.toString()
            binding.zAxis = chartDataItem?.z_axis.toString()
            dataNumber++
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
    fun setItems(chartDataListItems: List<DataMeasured>) {
        this.chartDataList.clear()
        this.chartDataList.addAll(chartDataListItems)
        notifyDataSetChanged()
    }

}
