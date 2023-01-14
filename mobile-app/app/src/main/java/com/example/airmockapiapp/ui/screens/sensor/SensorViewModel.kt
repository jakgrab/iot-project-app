package com.example.airmockapiapp.ui.screens.sensor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.local.SenseHatRepository
import com.example.airmockapiapp.data.model.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class SensorViewModel: ViewModel() {

    private val _sensorData = MutableStateFlow<SensorData?>(null)
    val sensorData = _sensorData.asStateFlow()

    private val _callingState = MutableStateFlow(CallState.ACTIVE)
    val callingState = _callingState.asStateFlow()

    private val repository = SenseHatRepository()

    fun getSensorData() {
        //val dataCaller = SensorsDataCaller(callingState, repository)
        //val data = dataCaller.callSensorData(1000L)
        viewModelScope.launch(Dispatchers.IO) {
//            data.collect {
//                collectSensorData(it)
//            }
        }
    }

    private fun collectSensorData(response: Response<SensorData>) {
        if (response.isSuccessful) {
            _sensorData.value = response.body()

        } else {
            Log.d("tag", "response unsuccessful! ${response.body()}")
        }
    }

    fun cancelDataStream() {
        _callingState.value = CallState.INACTIVE
    }
    fun resumeDataStream() {
        _callingState.value = CallState.ACTIVE
    }
}