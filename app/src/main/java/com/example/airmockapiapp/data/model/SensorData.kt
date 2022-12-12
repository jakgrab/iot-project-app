package com.example.airmockapiapp.data.model

data class SensorData(
    val temp: Float,
    val press: Float,
    val hum: Float,
    val roll: Float,
    val pitch: Float,
    val yaw: Float,
    val joy_up: String,
    val joy_down: String,
    val joy_left: String,
    val joy_right: String
)
