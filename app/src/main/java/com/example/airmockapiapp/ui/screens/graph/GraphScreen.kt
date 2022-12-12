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
import androidx.navigation.NavHostController
import com.example.airmockapiapp.ui.navigation.AirScreens
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun GraphScreen(viewModel: GraphViewModel, navController: NavHostController) {

    val graphData = viewModel.graphData.collectAsState()
    val lineData = viewModel.lineData.collectAsState()

    val initialDataForChart = LineData(
        LineDataSet(
            listOf(Entry(0f,0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f,0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f,0f)),
            "Initial"
        )
    )

    val rollPitchYawLineData = remember(graphData.value) {
        mutableStateOf(lineData.value)
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
                    it.data = rollPitchYawLineData.value ?: initialDataForChart//lineData.value//LineData(lineDataSet.value)
                    it.invalidate()
                }
            )
        } else {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(250.dp))
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

        Button(onClick = { navController.navigate(AirScreens.LedScreen.name) }) {
            Text(text = "Go to LEDScreen")
        }
//        Button(
//            onClick = {
//                Log.d("Tag", "Data: ${rollPitchYawLineData.value}")
//            }
//        ) {
//            Text(text = "Get log")
//        }
    }

}