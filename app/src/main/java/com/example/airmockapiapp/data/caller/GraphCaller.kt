package com.example.airmockapiapp.data.caller

import com.example.airmockapiapp.data.model.GraphResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GraphCaller {
    fun callGraphData(delay: Long, query: String): Flow<Response<List<GraphResponse>>>

    //fun callSensorData(delay: Long): Flow<Response<SensorData>>
}