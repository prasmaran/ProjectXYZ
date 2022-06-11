package com.example.projectxyz.utils.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import coil.request.SuccessResult
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
//        dataFeedReference.child(newPatient.patient_name.toString()).child("patient").setValue(newPatient.patient_name)
//        dataFeedReference.child(newPatient.patient_name.toString()).child("ic_number").setValue(newPatient.patient_ic_number)
//        dataFeedReference.child(newPatient.patient_name.toString()).child("gender").setValue(newPatient.patient_gender)
//        dataFeedReference.child(newPatient.patient_name.toString()).child("age").setValue(newPatient.patient_age)
//        dataFeedReference.child(newPatient.patient_name.toString()).child("illness").setValue(newPatient.patient_illness)
    }

}