package com.example.projectxyz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectxyz.model.user_list.UserList
import com.example.projectxyz.utils.repository.PatientListRepository

class HomeViewModel : ViewModel() {

    private val patientListRepository = PatientListRepository()

    private val _measuredDataList = MutableLiveData<List<UserList>>()
    val measuredDataList: LiveData<List<UserList>> = _measuredDataList

    fun fetchDataFeed() {
        patientListRepository.fetchDataFeed(_measuredDataList)
    }


}