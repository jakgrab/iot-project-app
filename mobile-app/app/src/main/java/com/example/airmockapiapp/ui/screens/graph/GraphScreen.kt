package com.example.airmockapiapp.ui.screens.graph

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.airmockapiapp.R
import com.example.airmockapiapp.ui.screens.MainViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


@Composable
fun GraphScreen(viewModel: MainViewModel, navController: NavHostController) {

    val context = LocalContext.current
    val graphData = viewModel.graphData.collectAsState()
    val lineData = viewModel.lineData.collectAsState()
    val lineDataSets = viewModel.lineDataSets.collectAsState()

    val llinedata = convertToLineData(context, lineDataSets.value)

    val initialDataForChart = LineData(
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        )
    )

    val rollPitchYawLineData = remember(graphData.value) {
        mutableStateOf(llinedata)
        //mutableStateOf(lineData.value)
    }

    val showGraph = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // we don't want to get any data if we don't observe it
                            viewModel.cancelDataStream()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "navigate back"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.calculateTopPadding()),
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
                    update = { lineChart ->
                        Log.d("tag", "Update called")
                        lineChart.data = rollPitchYawLineData.value
                            ?: initialDataForChart//lineData.value//LineData(lineDataSet.value)
                        lineChart.invalidate()
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            AnimatedVisibility(visible = !showGraph.value) {
                Button(
                    onClick = {
                        viewModel.getSensorData()
                        showGraph.value = true
                    }
                ) {
                    Text("Start Graph")
                }
            }


            Spacer(modifier = Modifier.height(40.dp))

            AnimatedVisibility(visible = showGraph.value) {
                Button(
                    onClick = {
                        showGraph.value = false
                        // TODO commented for now
                        //viewModel.cancelDataStream()
                    }
                ) {
                    Text(text = "Stop Graph")
                }
            }

        }

    }


}
fun convertToLineData(context: Context, lineDataSets: List<LineDataSet>?): LineData {
    return if (!lineDataSets.isNullOrEmpty()) LineData(
        lineDataSets[0].apply {
            color = ContextCompat.getColor(context, R.color.roll)
        },
        lineDataSets[1].apply {
            color = ContextCompat.getColor(context, R.color.pitch)
        },
        lineDataSets[2].apply {
            color = ContextCompat.getColor(context, R.color.yaw)
        },
    ) else  LineData(
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        ),
        LineDataSet(
            listOf(Entry(0f, 0f)),
            "Initial"
        )
    )
}