package com.example.projectxyz.model.user_list

data class UserList(
    val patient: String? = null,
    val data: HashMap<String, DataMeasured>? = null
)
