package com.example.airmockapiapp.ui.screens.graph

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.airmockapiapp.ui.screens.ApiViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun GraphScreen(viewModel: ApiViewModel) {

    val graphData = viewModel.graphData.collectAsState()
    val lineData = viewModel.lineData.collectAsState()

    val initialDataForChart = LineData(
        LineDataSet(
            listOf(Entry(0f,0f)),
            "Initial"
        )
    )

    val temperatureLineData = remember(graphData.value) {
        mutableStateOf<LineData?>(lineData.value)
    }

    val showGraph = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showGraph.value) {

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                factory = { context ->
                    LineChart(context).apply {
                        data =
                            initialDataForChart//lineData.value//LineData(lineDataSet.value)
                        invalidate()
                    }
                },
                update = {
                    Log.d("tag", "Update called")
                    it.data = temperatureLineData.value ?: initialDataForChart//lineData.value//LineData(lineDataSet.value)
                    it.invalidate()
                }
            )
        }

        Button(
            onClick = {
                viewModel.getGraphData()
                showGraph.value = true
            }
        ) {
            Text("Start Graph")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                showGraph.value = false
                viewModel.cancelGraphDataStream()
            }
        ) {
            Text(text = "Stop graph")
        }


        Button(
            onClick = {
                Log.d("Tag", "Data: ${temperatureLineData.value}")
            }
        ) {
            Text(text = "Get log")
        }
    }

}