package com.example.projectxyz.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentCreatePatientBinding

class CreatePatientFragment : Fragment() {

    private var _binding: FragmentCreatePatientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(CreatePatientViewModel::class.java)

        _binding = FragmentCreatePatientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setting up the action bar
        // (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.createPatientFragToolbar.toolbarTitle.text = getString(R.string.title_create_patient)

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}