package com.example.projectxyz.utils.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectxyz.R
import com.example.projectxyz.databinding.PatientListRowItemBinding
import com.example.projectxyz.databinding.TestingLayoutBinding
import com.example.projectxyz.model.user_list.UserList
import java.util.*

class HomeFragmentRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val patientListItem = mutableListOf<UserList>()

    inner class PatientListItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.testing_layout, parent, false)
    ) {

        private val binding = TestingLayoutBinding.bind(itemView)

        fun onBind(patientListItem: UserList){
            binding.patientNameTextView.text = patientListItem.patient.toString()
            binding.patientIcNumberTextView.text =  patientListItem.data?.get("data1").toString()
            binding.patientGenderTextView.text =  "Male"
            binding.patientIllnessTextView.text = "Asthma"

//            val rnd = Random()
//            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
//            binding.image.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PatientListItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PatientListItemViewHolder).onBind(patientListItem[position])
    }

    override fun getItemCount(): Int {
        return patientListItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(patientListItems: List<UserList>) {
        this.patientListItem.clear()
        this.patientListItem.addAll(patientListItems)
        notifyDataSetChanged()
    }
}