package com.example.projectxyz.ui.patientList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentPatientListBinding
import com.example.projectxyz.model.user_list.DataMeasured
import com.example.projectxyz.utils.adapter.ChartDataRVAdapter
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

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        val adapter = ChartDataRVAdapter()
        binding.chartDataRv.adapter = adapter

        val data = args.userData.data?.toSortedMap()
        val entries: List<DataMeasured>? = data?.keys?.map { data[it]!! }

        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.CEILING

        val meanValue = entries?.map { dataMeasured ->
            df.format(
                sqrt(
                    dataMeasured.component1().pow(2) +
                            dataMeasured.component2().pow(2) +
                            dataMeasured.component3().pow(2)
                )
            ).toDouble()
        }

        setDataItems(meanValue)

        args.userData.also {
            binding.patientNameTv.text = it.patient_name.toString()
            binding.patientIcTv.text = it.patient_ic_number.toString()
            binding.patientAgeTv.text = it.patient_age.toString()
            binding.patientGenderTv.text = it.patient_gender.toString()
            binding.patientDiseasesTv.text = it.patient_illness.toString()
        }

        if (entries != null) {
            adapter.setItems(entries)
        }

        "${args.userData.patient_name}'s data chart".also {
            binding.patientListFragToolbar.toolbarTitle.text = it
        }


        return root
    }

    private fun setDataItems(meanValue: List<Double>?) {

        val xValue = ArrayList<String>()
        val lineEntry = ArrayList<Entry>()

        meanValue?.forEachIndexed { index, mean ->
            xValue.add("Data${index + 1}")
            lineEntry.add(Entry((index + 1).toFloat(), mean.toFloat()))
        }
        println(meanValue)

        val lineDataSet = LineDataSet(lineEntry, args.userData.patient_name.toString())

        lineDataSet.apply {
            color = ContextCompat.getColor(requireContext(), R.color.darkPink)
            lineWidth = 2f
            isHighlightEnabled = true
            setDrawHighlightIndicators(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER

        }

        val data = LineData(lineDataSet)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
