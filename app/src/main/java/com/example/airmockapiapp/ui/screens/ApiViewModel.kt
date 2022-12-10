package com.example.airmockapiapp.ui.screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.remote.ApiInterface
import com.example.airmockapiapp.data.CallState
import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.DataCaller
import com.example.airmockapiapp.data.local.GraphRepository
import com.example.airmockapiapp.data.model.GraphResponse
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class ApiViewModel: ViewModel() {

    private val _responseState = MutableStateFlow<String?>(null)
    val responseState = _responseState.asStateFlow()

    private val _callingState = MutableStateFlow(CallState.ACTIVE)
    val callingState = _callingState.asStateFlow()

    private val _graphData = MutableStateFlow<GraphResponse?>(null)
    val graphData = _graphData.asStateFlow()

    private val _yValueList = MutableStateFlow<MutableList<Float>>(mutableListOf())
    val yValueList = _yValueList.asStateFlow()

    private val _xValueList = MutableStateFlow<MutableList<Float>>(mutableListOf())
    val xValueList = _xValueList.asStateFlow()

    // FOR MPANDROID
    private val _listOfEntries = mutableListOf<Entry>()

    private val _lineDataSet = MutableStateFlow<LineDataSet?>(null)
    val lineDataSet = _lineDataSet.asStateFlow()

    private val _lineData = MutableStateFlow<LineData?>(null)
    val lineData = _lineData.asStateFlow()

    private val repository = GraphRepository()


    fun getGraphData() {
        val dataCaller = DataCaller(callingState, repository)
        val data = dataCaller.call(5000L, "Hi")
        viewModelScope.launch(Dispatchers.IO) {
            data.collect {
                collectData(it)
            }
        }
    }

    private fun collectData(response: Response<GraphResponse>) {
        if (response.isSuccessful) {
            _graphData.value = response.body()
            _graphData.value?.let { addDataToGraph(it) }
        } else {
            Log.d("tag", "Response unsuccessful! ${response.body()}")
        }
    }

    private fun addDataToGraph(data: GraphResponse) {
        if (_listOfEntries.size >= Constants.GRAPH_VALUES_MAX_SIZE)
            _listOfEntries.removeAt(0)
        _listOfEntries.add(Entry(data.x,data.y))
        Log.d("tag", "Added entry ${_listOfEntries.last()}")
        convertEntriesToLineDataSet(_listOfEntries)
        Log.d("tag", "Converted to lineDataSet $_lineDataSet")


    }

    private fun convertEntriesToLineDataSet(listOfEntries: MutableList<Entry>) {
        _lineData.value = LineData(LineDataSet(listOfEntries, "temperature"))
        //_lineDataSet.value = LineDataSet(listOfEntries, "Temperature")
        //convertLineDataSetToLineData(_lineDataSet.value)
    }

    private fun convertLineDataSetToLineData(lineDataSet: LineDataSet) {
        _lineData.value = LineData(lineDataSet)
    }


    // not used right now

    private fun addValuesToGraph(data: GraphResponse) {
        if (_yValueList.value.size <= Constants.GRAPH_VALUES_MAX_SIZE) {
            _yValueList.value.add(data.y)
            _xValueList.value.add(data.x)
        } else {
            _yValueList.value.removeAt(0)
            _yValueList.value.add(data.y)

            _xValueList.value.removeAt(0)
            _xValueList.value.add(data.x)
        }
        Log.d("Tag","Adding values to feed graph. " +
                "Y value: ${_yValueList.value.last()}, X value: ${_xValueList.value.last()}")
    }

    private fun getXlabel(): String {
        val simpleDateFormat = SimpleDateFormat("HH-mm-ss", Locale.GERMANY)
        val currentTimeMillis = System.currentTimeMillis()
        return simpleDateFormat.format(currentTimeMillis)
    }

    fun cancelGraphDataStream() {
        _callingState.value = CallState.INACTIVE
    }
    fun resumeGraphDataStream() {
        _callingState.value = CallState.ACTIVE
    }

    // OLD getData
    fun getData(apiInterface: ApiInterface) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getData()

            if (response.isSuccessful) {

                //Log.d("API", "Response: ${response.body()?.string()}")
                // NOTE: Somehow response.body.string can be only called once

                val result = response.body()?.string()
                if (result != null) {
                    _responseState.emit(result)
                }
                Log.d("API", "responseState.value = ${_responseState.value}")
            } else {
                _responseState.value = "Api call unsuccessful, error: ${response.code()}"
            }
        }
    }

}