package com.example.airmockapiapp.ui.screens.graph

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.remote.ApiInterface
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.caller.DataCaller
import com.example.airmockapiapp.data.local.SenseHatRepository
import com.example.airmockapiapp.data.model.SensorBody
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

// TODO api will be changed again, do not fix now

class GraphViewModel : ViewModel() {

    private val _responseState = MutableStateFlow<String?>(null)
    val responseState = _responseState.asStateFlow()

    private val _callingState = MutableStateFlow(CallState.ACTIVE)
    val callingState = _callingState.asStateFlow()

    private val _graphData = MutableStateFlow<List<SensorBody>?>(null)
    val graphData = _graphData.asStateFlow()

    // FOR MPANDROID
    private val _rollList = mutableListOf<Entry>()
    private val _pitchList = mutableListOf<Entry>()
    private val _yawList = mutableListOf<Entry>()
    private val _listOfEntries = mutableListOf(_rollList, _pitchList, _yawList)

    private val _lineDataSet = MutableStateFlow<LineDataSet?>(null)
    val lineDataSet = _lineDataSet.asStateFlow()

    private val _lineData = MutableStateFlow<LineData?>(null)
    val lineData = _lineData.asStateFlow()

    private val repository = SenseHatRepository()


    fun getGraphData() {
        //val dataCaller = DataCaller(callingState, repository)
        //val data = dataCaller.callSensorData()
        viewModelScope.launch(Dispatchers.IO) {
            //data.collect {
                //collectData(it)
            //}
        }
    }

    private fun collectData(response: Response<List<SensorBody>>) {
        if (response.isSuccessful) {
            _graphData.value = response.body()
            Log.d("tag", "Response: ${response.body()}")
            _graphData.value?.let { addDataToGraph(it) }
        } else {
            Log.d("tag", "Response unsuccessful! ${response.body()}")
        }
    }

    private fun addDataToGraph(data: List<SensorBody>) {
        if (isListTooBig(_listOfEntries)) {
            Log.d("tag", "Too big, removing values")
            _listOfEntries.forEach { it.removeAt(0) }
        }

//        _rollList.add(Entry(data[0].x, data[0].y))
//        _pitchList.add(Entry(data[1].x, data[1].y))
//        _yawList.add(Entry(data[2].x, data[2].y))

        Log.d("tag", "First list: ${_listOfEntries[0]}")
        Log.d("tag", "Second list: ${_listOfEntries[1]}")
        Log.d("tag", "Third list: ${_listOfEntries[2]}")
        Log.d(
            "tag",
            "Sizes: ${_listOfEntries[0].size}, ${_listOfEntries[1].size}, ${_listOfEntries[2].size}"
        )

        convertEntriesToLineData(_listOfEntries)
        Log.d("tag", "Converted to lineData")
    }

    private fun isListTooBig(listOfEntries: MutableList<MutableList<Entry>>): Boolean {
        return listOfEntries.all { it.size >= Constants.GRAPH_VALUES_MAX_SIZE }
    }

    private fun convertEntriesToLineData(listOfEntries: MutableList<MutableList<Entry>>) {
        _lineData.value = LineData(
            LineDataSet(listOfEntries[0], "Roll"),
            LineDataSet(listOfEntries[1], "Pitch"),
            LineDataSet(listOfEntries[2], "Yaw"),
        )
    }

//    private fun convertLineDataSetToLineData(lineDataSet: LineDataSet) {
//        _lineData.value = LineData(lineDataSet)
//    }


    // not used right now

//    private fun addValuesToGraph(data: GraphResponse) {
//        if (_yValueList.value.size <= Constants.GRAPH_VALUES_MAX_SIZE) {
//            _yValueList.value.add(data.y)
//            _xValueList.value.add(data.x)
//        } else {
//            _yValueList.value.removeAt(0)
//            _yValueList.value.add(data.y)
//
//            _xValueList.value.removeAt(0)
//            _xValueList.value.add(data.x)
//        }
//        Log.d("Tag","Adding values to feed graph. " +
//                "Y value: ${_yValueList.value.last()}, X value: ${_xValueList.value.last()}")
//    }

//    private fun getXlabel(): String {
//        val simpleDateFormat = SimpleDateFormat("HH-mm-ss", Locale.GERMANY)
//        val currentTimeMillis = System.currentTimeMillis()
//        return simpleDateFormat.format(currentTimeMillis)
//    }

    fun cancelGraphDataStream() {
        _callingState.value = CallState.INACTIVE
    }

    fun resumeGraphDataStream() {
        _callingState.value = CallState.ACTIVE
    }

}