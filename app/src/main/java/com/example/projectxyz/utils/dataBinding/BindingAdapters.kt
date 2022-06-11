package com.example.projectxyz.utils.dataBinding

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectxyz.R
import com.example.projectxyz.model.user_list.UserList
import com.example.projectxyz.ui.home.HomeFragmentDirections
import com.example.projectxyz.utils.adapter.HomeFragmentRVAdapter

//class BindingAdapters {
//
//    companion object {
//
//        @BindingAdapter("setListItems")
//        @JvmStatic
//        fun setListItems(recyclerView: RecyclerView, itemList: List<UserList>?) {
//            (recyclerView.adapter as HomeFragmentRVAdapter).setItems(itemList!!)
//        }
//
//    }
//
//}

@BindingAdapter("navigateToChartsFragment")
fun navigateToChartsFragment(constraintLayout: ConstraintLayout, userList: UserList ) {
    constraintLayout.setOnClickListener {
        try {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationPatientList(userList)
            it.findNavController().navigate(action)
        } catch (e: Exception) {
            Log.e("TAG-navigateChart", e.toString())
        }
    }
}


@BindingAdapter("setItemsRV")
fun setItemsRV(recyclerView: RecyclerView, itemList: List<UserList>?) {
    if (itemList != null) {
        (recyclerView.adapter as HomeFragmentRVAdapter).setItems(itemList)
    }
}

@BindingAdapter("loadImageFromDrawable")
fun loadImageFromDrawable(imageView: ImageView, gender: String) {

    val femaleUser =
        "https://firebasestorage.googleapis.com/v0/b/tester-7b593.appspot.com/o/ic_user_female.PNG?alt=media&token=1b0489bd-3411-4618-b5e6-60953f0cc5eb"
    val maleUser =
        "https://firebasestorage.googleapis.com/v0/b/tester-7b593.appspot.com/o/ic_user_male.PNG?alt=media&token=5fe8f3ae-79bf-4bb6-aa9b-9d08708693ea"

    val finalImage = if (gender.toLowerCase().contains("female")) femaleUser else maleUser
    imageView.load(finalImage) {
        placeholder(R.drawable.downloading)
        error(R.drawable.ic_user_male)
        crossfade(300)
    }
}