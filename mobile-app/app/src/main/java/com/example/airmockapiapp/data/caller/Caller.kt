package com.example.airmockapiapp.data.caller

import com.example.airmockapiapp.data.model.ColorStatus
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

interface Caller {
    fun callSensorData(callingState: StateFlow<CallState>, delay: Float): Flow<Response<SensorData>>

    fun callLedColors(callingState: StateFlow<CallState>): Flow<Response<ColorStatus>>
}