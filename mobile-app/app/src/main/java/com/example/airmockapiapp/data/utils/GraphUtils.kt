package com.example.airmockapiapp.data.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.example.airmockapiapp.R
import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.caller.CallState
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GraphUtils {//@Inject constructor(@ApplicationContext val myContext: Context){

    //    fun addDataToGraph(
//        data: List<Float>,
//        xValue: Float,
//        rollList: MutableList<Entry>,
//        pitchList: MutableList<Entry>,
//        yawList: MutableList<Entry>,
//        listOfEntries: MutableList<MutableList<Entry>>,
//        callingState: MutableStateFlow<CallState>
//    ) {
//        if (isListTooBig(listOfEntries)) {
//            Log.d("tag", "Too big, removing values")
//            listOfEntries.forEach { it.removeAt(0) }
//        }
//
//        rollList.add(Entry(getXlabel(xValue, callingState.value), data[0]))
//        pitchList.add(Entry(getXlabel(xValue, callingState.value), data[1]))
//        yawList.add(Entry(getXlabel(xValue, callingState.value), data[2]))
//
//        convertEntriesToLineData(listOfEntries)
//    }

    fun getXlabel(xValue: Float, callingStateValue: CallState): Float {
        val newXValue = xValue + 1f
        Log.d("tag","X value: $newXValue")
        return if (callingStateValue == CallState.INACTIVE) 0f else newXValue
    }

    fun isListTooBig(listOfEntries: MutableList<MutableList<Entry>>): Boolean {
        return listOfEntries.all { it.size >= Constants.GRAPH_VALUES_MAX_SIZE }
    }

}