package com.example.airmockapiapp.data.caller

import com.example.airmockapiapp.data.model.SensorBody
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SensorsCaller {
    fun callSensorData(sensorBody: SensorBody, delay: Long): Flow<Response<SensorData>>
}