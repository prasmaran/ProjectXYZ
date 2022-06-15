@file:Suppress("IllegalIdentifier")

package com.example.projectxyz.model.user_list

import android.os.Parcelable

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UserList(
    val patient_name: String? = null,
    val patient_ic_number: String? = null,
    val patient_age: String? = null,
    val patient_gender: String? = null,
    val patient_illness: String? = null,
    val data: @RawValue HashMap<String, DataMeasured>? = null
) : Parcelable
