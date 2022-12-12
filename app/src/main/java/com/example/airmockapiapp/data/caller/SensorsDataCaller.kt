package com.example.airmockapiapp.data.caller

import android.util.Log
import com.example.airmockapiapp.data.local.AirRepository
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class SensorsDataCaller(
    private val callingState: StateFlow<CallState>,
    private val repository: AirRepository
) : SensorsCaller{

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun callSensorData(delay: Long): Flow<Response<SensorData>> {
        return channelFlow {
            while (!isClosedForSend) {
                if (callingState.value == CallState.INACTIVE) {
                    Log.d("Tag", "CALLER STOPPING")
                    close()
                    return@channelFlow
                }
                Log.d("Tag", "CALLER RUNNING")
                delay(delay)
                send(repository.getSensorData())
            }
        }.flowOn(Dispatchers.IO)
    }
}