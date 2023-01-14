package com.example.airmockapiapp.data.model

data class ColorData(
    val requests: List<LedParams>
)
data class LedParams(
    val position: List<Int>,
    val rgb: List<Int>
)
