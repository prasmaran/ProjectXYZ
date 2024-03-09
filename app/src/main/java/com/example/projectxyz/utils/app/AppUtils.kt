package com.example.projectxyz.utils.app

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

class AppUtils {

    companion object {

        // Toast message function
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        // Snack bar message function
        fun View.showSnackBar(msg: String) {
            Snackbar.make(this, msg, Snackbar.LENGTH_LONG).also { snackbar ->
                snackbar.setAction("OKAY") {
                    snackbar.dismiss()
                }
            }.show()
        }

        // handle lineDataSet properties
        fun <T: Any, R: Any> Collection<T?>.whenAllNotNull(block: (List<T>)->R) {
            if (this.all { it != null }) {
                block(this.filterNotNull()) // or do unsafe cast to non null collection
            }
        }

        // Navigate to another activity function
        fun <T : AppCompatActivity> AppCompatActivity.navigateTo(cls: Class<T>, clearTask: Boolean = false) {
            val intent = Intent(this, cls)
            if (clearTask) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }


        // Custom dialog fragment function
        fun showDialogFragment(
            dialogFragment: DialogFragment,
            fragmentManager: FragmentManager,
            fragTag: String
        ) {
            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            val prev: Fragment? =
                fragmentManager.findFragmentByTag(fragTag)
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            dialogFragment.show(ft, fragTag)
        }

    }

}