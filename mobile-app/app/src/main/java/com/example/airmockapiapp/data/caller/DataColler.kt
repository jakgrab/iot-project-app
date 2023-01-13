package com.example.airmockapiapp.data.caller

import android.util.Log
import com.example.airmockapiapp.data.local.SenseHatRepository
import com.example.airmockapiapp.data.model.ColorStatus
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class DataCaller(
    //private val viewModel: ApiViewModel,
    private val callingState: StateFlow<CallState>,
    private val repository: SenseHatRepository
) : Caller {


    // Returns channelFlow which calls API in a loop with a delay

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
                send(
                    repository.getSensorData("", "", "", "", ""))
            }
        }.flowOn(Dispatchers.IO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun callLedColors(delay: Long): Flow<Response<ColorStatus>> {
        return channelFlow {
            while (!isClosedForSend) {
                if (callingState.value == CallState.INACTIVE) {
                    Log.d("Tag", "CALLER STOPPING")
                    close()
                    return@channelFlow
                }
                Log.d("Tag", "CALLER RUNNING")
                delay(delay)
                send(repository.getLedColors())
            }
        }.flowOn(Dispatchers.IO)
    }
}