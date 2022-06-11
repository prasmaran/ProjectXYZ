package com.example.projectxyz.ui.patientList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentPatientListBinding

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
            ViewModelProvider(this).get(PatientListViewModel::class.java)

        _binding = FragmentPatientListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val data = args.userData.data?.toSortedMap()
        binding.textDashboard.text = data.toString()
        println(data)

        // Setting up the action bar
        // (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.patientListFragToolbar.toolbarTitle.text = getString(R.string.title_patient_list)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}