package com.example.projectxyz.ui.base

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projectxyz.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun showErrorSnackBar(message: String, error: Boolean) {

        val snackBar =
            activity?.let { Snackbar.make(it.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG) }
        val snackBarView = snackBar?.view

        if(error){
            snackBarView?.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else {
            snackBarView?.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        snackBarView?.minimumHeight = activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.height!!
        snackBar?.show()
    }


}