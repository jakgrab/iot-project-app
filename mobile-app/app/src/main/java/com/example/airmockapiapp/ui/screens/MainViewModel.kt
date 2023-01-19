package com.example.airmockapiapp.ui.screens

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.caller.DataCaller
import com.example.airmockapiapp.data.local.SenseHatRepository
import com.example.airmockapiapp.data.model.*
import com.example.airmockapiapp.data.utils.GraphUtils
import com.example.airmockapiapp.data.utils.LedUtils
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val repository = SenseHatRepository()

    var ddelay: Float = 3f

    private val graphUtils = GraphUtils()
    private val ledUtils = LedUtils()

    private var xValue: Float = 0f

    private val _callingState = MutableStateFlow(CallState.ACTIVE)
    val callingState = _callingState.asStateFlow()

    private val _sensorData = MutableStateFlow<SensorData?>(null)
    val sensorData = _sensorData.asStateFlow()

    private val _graphData = MutableStateFlow<List<Float>?>(null)
    val graphData = _graphData.asStateFlow()

    private val _ledData = MutableStateFlow<List<List<Float>>?>(null)
    val ledData = _ledData.asStateFlow()

    // MPAndroid
    private val _rollList = mutableListOf<Entry>()
    private val _pitchList = mutableListOf<Entry>()
    private val _yawList = mutableListOf<Entry>()
    private val _listOfEntries = mutableListOf(_rollList, _pitchList, _yawList)

    private val _lineDataSets = MutableStateFlow<List<LineDataSet>?>(null)
    val lineDataSets = _lineDataSets.asStateFlow()

    private val _lineData = MutableStateFlow<LineData?>(null)
    val lineData = _lineData.asStateFlow()


    private val dataCaller = DataCaller(repository)


    fun getSensorData() {
        val sensorData = dataCaller.callSensorData(_callingState, ddelay)

        viewModelScope.launch {
            sensorData.collect {
                collectSensorData(it)
            }
        }
    }

    fun getLedStatus() {
        val ledData = dataCaller.callLedColors(_callingState)

        viewModelScope.launch {
            ledData.collect {
                collectLedData(it)
            }
        }
    }

    private fun collectSensorData(response: Response<SensorData>) {
        if (response.isSuccessful) {
            _graphData.value = response.body()?.orientation
            _sensorData.value = response.body()

            Log.d("tag", "Response: ${response.body()}")

            _graphData.value?.let { addDataToGraph(it) }
        } else {
            Log.d("tag", "Response unsuccessful! ${response.body()}")
        }
    }

    private fun collectLedData(response: Response<ColorStatus>) {
        if (response.isSuccessful) {
            if (!response.body()?.diodes.isNullOrEmpty())
                _ledData.value = response.body()?.diodes

            Log.d("tag", "Response: ${response.body()}")

        } else {
            Log.d("tag", "Response unsuccessful! ${response.body()}")
        }
    }

    /////// Graph ///////
    private fun addDataToGraph(data: List<Float>) {
        if (graphUtils.isListTooBig(_listOfEntries)) {
            Log.d("tag", "Too big, removing values")
            _listOfEntries.forEach { it.removeAt(0) }
        }

        val xValue = getXlabel()
        _rollList.add(
            Entry(xValue, data[0])
        )
        _pitchList.add(
            Entry(xValue, data[1])
        )
        _yawList.add(
            Entry(xValue, data[2])
        )

        _lineDataSets.value = convertEntriesToLineDataSets(_listOfEntries)

    }

    private fun getXlabel(): Float {
        return if (_callingState.value == CallState.INACTIVE) 0f else xValue++
    }

    private fun convertEntriesToLineDataSets(
        listOfEntries: MutableList<MutableList<Entry>>
    ): List<LineDataSet> {

        return listOf(
            LineDataSet(listOfEntries[0], "Roll"),
            LineDataSet(listOfEntries[1], "Pitch"),
            LineDataSet(listOfEntries[2], "Yaw")
        )
    }

    fun postLedColors(
        indexList: List<Int>,
        colorList: List<Color>
    ) {
        val colorData = ledUtils.toColorData(indexList, colorList)
        colorData.requests.forEach {
            Log.d("LED", "colorData: ${it.position}")
            Log.d("LED", "colorData: ${it.rgb}")

        }
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postLedColors(colorData)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("tag", "Upload successful, message: ${response.code()}")
                } else {
                    Log.d("tag", "Upload failed, message: ${response.body()}")
                }
            }
        }
    }

    fun convertToColorList(diodeList: List<List<Float>>): MutableList<Color> {
        return ledUtils.convertToColors(diodeList)
    }

    fun cancelDataStream() {
        _callingState.value = CallState.INACTIVE
        Log.d("Data stream", "DATA STREAM INACTIVE")
    }

    fun resumeDataStream() {
        _callingState.value = CallState.ACTIVE
        Log.d("Data stream", "DATA STREAM ACTIVE")
    }

}