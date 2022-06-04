package com.example.projectxyz.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatePatientViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is create patient fragment"
    }
    val text: LiveData<String> = _text
}