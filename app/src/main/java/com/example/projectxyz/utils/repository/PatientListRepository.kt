package com.example.projectxyz.utils.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectxyz.model.user_list.UserList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.reflect.typeOf

class PatientListRepository {

    private val database = Firebase.database
    private val dataFeedReference = database.getReference("patients-xyz")

    fun fetchDataFeed(liveData: MutableLiveData<List<UserList>>) {
        dataFeedReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val retrievedPatientListData: List<UserList> = snapshot.children.map { children ->
                        children.getValue(UserList::class.java)!!
                    }
                    Log.i("TAG-RETRIEVED", retrievedPatientListData.toString())
                    liveData.postValue(retrievedPatientListData)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
    }

}