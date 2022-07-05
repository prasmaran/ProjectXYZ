package com.example.projectxyz.ui.patientList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentPatientListBinding
import com.example.projectxyz.model.meanValue.MeanValueChartData
import com.example.projectxyz.model.user_list.DataMeasured
import com.example.projectxyz.utils.adapter.ChartDataRVAdapter
import com.example.projectxyz.utils.app.AppUtils.Companion.whenAllNotNull
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

class PatientsListFragment : Fragment() {

    private var _binding: FragmentPatientListBinding? = null
    private val args by navArgs<PatientsListFragmentArgs>()

    private val dataReference = arrayOf(
        "Baseline (Pre Knee Extension Test)",
        "Baseline (Pre Squat Test)",
        "Knee Extension Set 1 (With Load)",
        "Knee Extension Set (With Load)",
        "Knee Extension Set 3 (With Load)",
        "Baseline (Post Knee Extension Test)",
        "Half Squat (Without Load)",
        "Half Squat Set 1 (With Load)",
        "Half Squat Set 2 (With Load)",
        "Half Squat Set 3 (With Load)",
        "Baseline (Post Squat Test)",
        "Baseline (Post Knee Extension Test 2)"
    )

    private val meanValueList = mutableListOf<MeanValueChartData>()
    private val xValueList = mutableListOf<Double>()
    private val yValueList = mutableListOf<Double>()
    private val zValueList = mutableListOf<Double>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[PatientListViewModel::class.java]

        _binding = FragmentPatientListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val data = args.userData.data?.toSortedMap()
        val entries: List<DataMeasured>? = data?.keys?.map { data[it]!! }

        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.CEILING

        // Adding MMG values to a list
        val meanValue = entries?.map { dataMeasured ->

            val x = dataMeasured.component1()
            val y = dataMeasured.component2()
            val z = dataMeasured.component3()

            // In inner loop, append to x,y,z lists
            xValueList.add(x)
            yValueList.add(y)
            zValueList.add(z)

            df.format(
                sqrt(
                    x.pow(2) + y.pow(2) + z.pow(2)
                )
            ).toDouble()
        }

        if (meanValue != null) {
            for (i in meanValue.indices) {
                val itemMeanDataChart = MeanValueChartData(
                    dataReference[i],
                    entries[i].x_axis,
                    entries[i].y_axis,
                    entries[i].z_axis,
                    meanValue[i]
                )
                meanValueList.add(itemMeanDataChart)
            }
        }

        Log.i("MeanValueChartData", meanValueList.toString())
        Log.i("XValueChartData", xValueList.toString())
        Log.i("YValueChartData", yValueList.toString())
        Log.i("ZValueChartData", zValueList.toString())

        val adapter = ChartDataRVAdapter()
        binding.chartDataRv.adapter = adapter

        setDataItems(meanValue, xValueList, yValueList, zValueList)

        args.userData.also {
            binding.patientNameTv.text = it.patient_name.toString()
            binding.patientIcTv.text = it.patient_ic_number.toString()
            binding.patientAgeTv.text = it.patient_age.toString()
            binding.patientGenderTv.text = it.patient_gender.toString()
            binding.patientDiseasesTv.text = it.patient_illness.toString()
        }

        if (entries != null) {
            if (meanValue != null) {
                adapter.setItems(meanValueList)
            }
        }

        "${args.userData.patient_name}'s data chart".also {
            binding.patientListFragToolbar.toolbarTitle.text = it
        }

        return root
    }

    private fun setDataItems(
        meanValue: List<Double>?,
        xValues: List<Double>?,
        yValues: List<Double>?,
        zValues: List<Double>?,
    ) {

        val xValue = ArrayList<String>()
        val lineEntry = ArrayList<Entry>()

        // Adding 3 new graphs
        val xDataLineEntry = ArrayList<Entry>()
        val yDataLineEntry = ArrayList<Entry>()
        val zDataLineEntry = ArrayList<Entry>()

        meanValue?.forEachIndexed { index, mean ->
            xValue.add("Data${index + 1}")
            lineEntry.add(Entry((index + 1).toFloat(), mean.toFloat()))
        }
        xValues?.forEachIndexed { index, mean ->
            xDataLineEntry.add(Entry((index + 1).toFloat(), mean.toFloat()))
        }
        yValues?.forEachIndexed { index, mean ->
            yDataLineEntry.add(Entry((index + 1).toFloat(), mean.toFloat()))
        }
        zValues?.forEachIndexed { index, mean ->
            zDataLineEntry.add(Entry((index + 1).toFloat(), mean.toFloat()))
        }

        val lineDataSet = LineDataSet(lineEntry, args.userData.patient_name.toString())
        val xLineDataSet = LineDataSet(xDataLineEntry, args.userData.patient_name.toString())
        val yLineDataSet = LineDataSet(yDataLineEntry, args.userData.patient_name.toString())
        val zLineDataSet = LineDataSet(zDataLineEntry, args.userData.patient_name.toString())

        val colourList = listOf(
            R.color.darkPink,
            R.color.darkOrange,
            R.color.darkGreen,
            R.color.purple_500)

        listOf(lineDataSet, xLineDataSet, yLineDataSet, zLineDataSet).whenAllNotNull {
            it.forEachIndexed { index, lineDataSet ->
                lineDataSet.apply {
                    color = ContextCompat.getColor(requireContext(), colourList[index])
                    lineWidth = 2f
                    isHighlightEnabled = true
                    setDrawHighlightIndicators(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
            }
        }

        val data = LineData(lineDataSet)
        val xData = LineData(xLineDataSet)
        val yData = LineData(yLineDataSet)
        val zData = LineData(zLineDataSet)

        binding.lineChart.apply {
            this.data = data
            this.animateXY(2000, 2000)
            setTouchEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))

            xAxis.apply {
                isGranularityEnabled = true
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }
        }

        binding.lineChartX.apply {
            this.data = xData
            this.animateXY(2000, 2000)
            setTouchEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))

            xAxis.apply {
                isGranularityEnabled = true
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }
        }

        binding.lineChartY.apply {
            this.data = yData
            this.animateXY(2000, 2000)
            setTouchEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))

            xAxis.apply {
                isGranularityEnabled = true
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }
        }

        binding.lineChartZ.apply {
            this.data = zData
            this.animateXY(2000, 2000)
            setTouchEnabled(true)
            isDragEnabled = true
            setPinchZoom(true)
            description.isEnabled = false
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))

            xAxis.apply {
                isGranularityEnabled = true
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
