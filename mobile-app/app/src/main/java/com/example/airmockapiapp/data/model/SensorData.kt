package com.example.airmockapiapp.data.model

import com.google.gson.annotations.SerializedName


data class SensorData(
    val temperature: Float,
    val humidity: Float,
    val pressure: Float,
    val orientation: List<Float>,
    @SerializedName("joystick-click")
    val joystick_clicks: Int,
    @SerializedName("joystick-position")
    val joystick_position: List<Int>
)
