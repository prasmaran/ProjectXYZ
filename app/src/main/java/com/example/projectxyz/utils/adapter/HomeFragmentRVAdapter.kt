package com.example.projectxyz.utils.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectxyz.R
import com.example.projectxyz.databinding.PatientListRowItemBinding
import com.example.projectxyz.databinding.TestingLayoutBinding
import com.example.projectxyz.model.user_list.UserList
import java.util.*

class HomeFragmentRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val patientListItem = mutableListOf<UserList>()

    inner class PatientListItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.patient_list_row_item, parent, false)
    ) {

        private val binding = PatientListRowItemBinding.bind(itemView)

        fun onBind(patientListItem: UserList) {

            binding.userData = patientListItem

            if(patientListItem.patient_gender?.lowercase().toString().contains("female")){
                binding.patientImageView.setImageResource(R.drawable.ic_user_female)
            } else{
                binding.patientImageView.setImageResource(R.drawable.ic_user_male)
            }
            binding.patientName = patientListItem.patient_name.toString()
            binding.patientIcNumber = patientListItem.patient_ic_number.toString()
            binding.patientGender = patientListItem.patient_gender.toString()
            binding.patientIllness = patientListItem.patient_illness.toString()
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
