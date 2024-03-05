package com.example.projectxyz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectxyz.R
import com.example.projectxyz.databinding.FragmentHomeBinding
import com.example.projectxyz.utils.adapter.HomeFragmentRVAdapter
import com.example.projectxyz.utils.app.AppUtils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val root: View = binding.root

        // Setting up the action bar
        // (activity as AppCompatActivity).supportActionBar?.elevation = 0f
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.homeFragToolbar.toolbarTitle.text = getString(R.string.title_home)

        val adapter = HomeFragmentRVAdapter()
        binding.homeFragRv.adapter = adapter

        homeViewModel.fetchDataFeed()

        homeViewModel.measuredDataList.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.noListNotificationTv.visibility = View.VISIBLE
                AppUtils.showToast(requireContext(), "No patient list found")
            } else {
                binding.noListNotificationTv.visibility = View.GONE
                adapter.setItems(it)
            }
        }

        binding.fabBtn.setOnClickListener {
            binding.homeFragRv.smoothScrollToPosition(0)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}