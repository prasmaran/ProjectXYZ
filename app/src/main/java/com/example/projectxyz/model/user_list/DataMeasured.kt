package com.example.projectxyz.model.user_list

import com.google.firebase.database.PropertyName

data class DataMeasured (

    @get:PropertyName("X Axis")
    @PropertyName("X Axis") val x_axis: Double = 0.00,

    @get:PropertyName("Y Axis")
    @PropertyName("Y Axis") val y_axis: Double = 0.00,

    @get:PropertyName("Z Axis")
    @PropertyName("Z Axis") val z_axis: Double = 0.00
)

//data class DataMeasured(
//    val `X Axis`: Double = 0.00,
//    val `Y Axis`: Double = 0.00,
//    val `Z Axis`: Double = 0.00,
//)
