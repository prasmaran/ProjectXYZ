package com.example.projectxyz.ui.patientList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatientListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is patient list fragment"
    }
    val text: LiveData<String> = _text
}