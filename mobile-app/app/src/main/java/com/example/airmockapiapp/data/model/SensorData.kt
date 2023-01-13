package com.example.airmockapiapp.data.model

data class SensorData(
    val temperature: Float,
    val humidity: Float,
    val pressure: Float,
    val orientation: List<Float>,
    val joystick: List<Int>
)
