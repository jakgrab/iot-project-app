package com.example.airmockapiapp.data.model

data class ColorStatus(
    val diodes: List<List<Float>>
)

data class Diode(
    val colors: List<Float>
)