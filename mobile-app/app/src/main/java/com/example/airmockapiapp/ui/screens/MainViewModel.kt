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

    private val _delay = MutableStateFlow<Long>(1000L)
    val delay = _delay.asStateFlow()

    var ddelay: Long = 3000L

    private val graphUtils = GraphUtils()
    private val ledUtils = LedUtils()

    private var xValue: Float = 0f

    private val _responseState = MutableStateFlow<String?>(null)
    val responseState = _responseState.asStateFlow()

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

            //_ledData.value?.let { updateLeds(it) } forgot what it does
        } else {
            Log.d("tag", "Response unsuccessful! ${response.body()}")
        }
    }

    /////// Graph ///////
    private fun addDataToGraph(data: List<Float>) {
        //if (isListTooBig(_listOfEntries)) {
        if (graphUtils.isListTooBig(_listOfEntries)) {
            Log.d("tag", "Too big, removing values")
            _listOfEntries.forEach { it.removeAt(0) }
        }

//        _rollList.add(Entry(getXlabel(), data[0]))
//        _pitchList.add(Entry(getXlabel(), data[1]))
//        _yawList.add(Entry(getXlabel(), data[2]))

//        _rollList.add(
//            Entry(graphUtils.getXlabel(xValue, _callingState.value), data[0])
//        )
//        _pitchList.add(
//            Entry(graphUtils.getXlabel(xValue, _callingState.value), data[1])
//        )
//        _yawList.add(
//            Entry(graphUtils.getXlabel(xValue, _callingState.value), data[2])
//        )

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

        Log.d("tag", "First list: ${_listOfEntries[0]}")
        Log.d("tag", "Second list: ${_listOfEntries[1]}")
        Log.d("tag", "Third list: ${_listOfEntries[2]}")
//        Log.d(
//            "tag",
//            "Sizes: ${_listOfEntries[0].size}, ${_listOfEntries[1].size}, ${_listOfEntries[2].size}"
//        )

        //convertEntriesToLineData(_listOfEntries)
        _lineDataSets.value = convertEntriesToLineDataSets(_listOfEntries)
//        Log.d("tag", "Converted to lineData")
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

    // TODO graphUtils used instead
//    private fun getXlabel(): Float {
////        return if (_callingState.value == CallState.INACTIVE) 0f else xValue++
////    }
//
//    private fun isListTooBig(listOfEntries: MutableList<MutableList<Entry>>): Boolean {
//        return listOfEntries.all { it.size >= Constants.GRAPH_VALUES_MAX_SIZE }
//    }
//
//    private fun convertEntriesToLineData(listOfEntries: MutableList<MutableList<Entry>>) {
//        _lineData.value = LineData(
//            LineDataSet(listOfEntries[0], "Roll"),
//            LineDataSet(listOfEntries[1], "Pitch"),
//            LineDataSet(listOfEntries[2], "Yaw"),
//        )
//    }

    /////// Leds ///////
    // TODO can be put back in LedVM

    private fun updateLeds(ledColors: List<Diode>) {
        //_ledData.value = ledColors
    }

    fun postLedColors(
        indexList: List<Int>,
        colorList: List<Color>
    ) {

        val colorData = ledUtils.toColorData(indexList, colorList)

        viewModelScope.launch(Dispatchers.IO) {
//
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

    // TODO ledUtils used instead
//    private fun toColorData(
//        indexList: List<Int>,
//        colorList: List<Color>
//    ): List<ColorData> {
//
//        return indexList.zip(colorList) { index, color ->
//            ColorData(indexToPositionList(index), colorToRgbList(color))
//        }
//    }
//
//    private fun indexToPositionList(index: Int): List<Int> {
//        val row = index / 8
//        val column = index % 8
//        return listOf(row, column)
//    }
//
//    private fun colorToRgbList(color: Color): List<Int> {
//        return listOf(color.red.toInt(), color.green.toInt(), color.blue.toInt())
//    }

    fun cancelDataStream() {
        _callingState.value = CallState.INACTIVE
        Log.d("Data stream", "DATA STREAM INACTIVE")
    }

    fun resumeDataStream() {
        _callingState.value = CallState.ACTIVE
        Log.d("Data stream", "DATA STREAM ACTIVE")
    }
    /////// Sensors ///////

}