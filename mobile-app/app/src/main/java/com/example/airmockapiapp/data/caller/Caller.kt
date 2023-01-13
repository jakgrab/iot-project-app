package com.example.airmockapiapp.data.caller

import com.example.airmockapiapp.data.model.ColorStatus
import com.example.airmockapiapp.data.model.SensorBody
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Caller {
    fun callSensorData(delay: Long): Flow<Response<SensorData>>

    fun callLedColors(delay: Long): Flow<Response<ColorStatus>>
}