package com.example.airmockapiapp.data.model

data class ColorStatus(
    val diodes: List<Diode>
)

data class Diode(
    val colors: List<Float>
)