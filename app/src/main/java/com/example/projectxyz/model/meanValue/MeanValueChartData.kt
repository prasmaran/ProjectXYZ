package com.example.projectxyz.model.meanValue

import com.google.firebase.database.PropertyName

data class MeanValueChartData(

    val dataTestName: String? = "",

    @get:PropertyName("X Axis")
    @PropertyName("X Axis") val x_axis: Double? = 0.00,

    @get:PropertyName("Y Axis")
    @PropertyName("Y Axis") val y_axis: Double? = 0.00,

    @get:PropertyName("Z Axis")
    @PropertyName("Z Axis") val z_axis: Double? = 0.00,

    val meanValue: Double? = 0.00
)
