package com.example.projectxyz.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectxyz.model.user_list.UserList
import com.example.projectxyz.utils.repository.PatientListRepository

class CreatePatientViewModel : ViewModel() {

    private val patientListRepository = PatientListRepository()

    private val _successResult = MutableLiveData<Boolean>()
    val successResult: LiveData<Boolean> = _successResult


    fun createNewPatient(newPatient: Map<String, String?>){
        patientListRepository.createNewPatient(newPatient, _successResult)
    }


}