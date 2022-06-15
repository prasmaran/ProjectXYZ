@file:Suppress("IllegalIdentifier")

package com.example.projectxyz.utils.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.projectxyz.model.user_list.UserList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PatientListRepository {

    private val database = Firebase.database
    private val dataFeedReference = database.getReference("patients-xyz")

    fun fetchDataFeed(liveData: MutableLiveData<List<UserList>>) {
        dataFeedReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val retrievedPatientListData: List<UserList> =
                        snapshot.children.map { children ->
                            children.getValue(UserList::class.java)!!
                        }

                    Log.i("TAG-RETRIEVED", retrievedPatientListData.toString())

                    /**
                     * Posting 2 arguments as livedata
                     */
                    liveData.postValue(retrievedPatientListData)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
    }

    fun createNewPatient(newPatient: Map<String, String?>, liveData: MutableLiveData<Boolean>) {

        dataFeedReference.child(newPatient.getValue("patient_name").toString()).updateChildren(newPatient)
            .addOnSuccessListener {
                Log.i("TAG-SUCCESS-WRITE", "SUCCESSFULLY CREATED")
                liveData.postValue(true)
            }
            .addOnFailureListener {
                Log.i("TAG-FAIL-WRITE", "CREATION FAILED")
                liveData.postValue(false)
            }
    }

}