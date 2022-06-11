package com.example.projectxyz.model.new_user

import com.google.firebase.database.Exclude

data class NewPatient(
    val patient_name: String? = null,
    val patient_ic_number: String? = null,
    val patient_age: String? = null,
    val patient_gender: String? = null,
    val patient_illness: String? = null
) {
    @Exclude
    fun toMap(): Map<String, String?> {
        return mapOf(
            "patient_name" to patient_name,
            "patient_ic_number" to patient_ic_number,
            "patient_age" to patient_age,
            "patient_gender" to patient_gender,
            "patient_illness" to patient_illness
        )
    }
}
