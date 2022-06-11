package com.example.projectxyz.ui.create

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentCreatePatientBinding
import com.example.projectxyz.model.new_user.NewPatient
import com.example.projectxyz.ui.base.BaseFragment
import com.example.projectxyz.utils.app.AppUtils

class CreatePatientFragment : BaseFragment() {

    private var _binding: FragmentCreatePatientBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var patientName = ""
    private var patientAge = ""
    private var patientIC = ""
    private var patientIllness = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val createPatientViewModel: CreatePatientViewModel by lazy {
            ViewModelProvider(this)[CreatePatientViewModel::class.java]
        }

        _binding = FragmentCreatePatientBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setting up the action bar
        // (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.createPatientFragToolbar.toolbarTitle.text =
            getString(R.string.title_create_patient)

        binding.registerBtn.setOnClickListener {

            patientName = binding.patientNameEt.editText?.text.toString().trim()
            patientAge = binding.patientAgeEt.editText?.text.toString().trim()
            patientIC = binding.patientIcEt.editText?.text.toString().trim()
            patientIllness = binding.patientIllnessEt.editText?.text.toString().trim()

            if (validateCreatePatient()) {
                val newPatient = NewPatient(
                    patientName,
                    patientIC,
                    patientAge,
                    "Female",
                    patientIllness
                )
                val mappedNewUser = newPatient.toMap()

                createPatientViewModel.createNewPatient(mappedNewUser)
                createPatientViewModel.successResult.observe(viewLifecycleOwner) {
                    if(it){
                        AppUtils.showToast(requireContext(),"Successfully created new patient")
                        findNavController().navigate(R.id.action_navigation_create_patient_to_navigation_home)
                    } else {
                        AppUtils.showToast(requireContext(),"Failed creating new patient")
                    }
                }

            } else {
                Toast.makeText(requireContext(), "Not validated", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    /**
     * Validate the new patient details
     */
    private fun validateCreatePatient(): Boolean {
        return when {
            TextUtils.isEmpty(patientName.trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.enter_valid_name), true)
                false
            }
            TextUtils.isEmpty(patientAge.trim { it <= ' ' }) || patientAge.toInt() > 100 -> {
                showErrorSnackBar(resources.getString(R.string.enter_valid_age), true)
                false
            }
            TextUtils.isEmpty(patientIC.trim { it <= ' ' || it.toString().length > 12 }) -> {
                showErrorSnackBar(resources.getString(R.string.enter_valid_ic), true)
                false
            }
            TextUtils.isEmpty(patientIllness.trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.enter_valid_illness), true)
                false
            }
            else -> {
                showErrorSnackBar(resources.getString(R.string.validated), false)
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}