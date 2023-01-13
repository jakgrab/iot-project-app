package com.example.airmockapiapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airmockapiapp.ui.theme.AirMockApiAppTheme
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle


@Composable
fun GraphTest(
    xData: List<String>,
    yData: List<Int>,
    modifier: Modifier = Modifier,
    title: String,
    graphStyle: LineGraphStyle
) {


    val xGraphData = xData.map {
        GraphData.String(it)
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = title)

        LineGraph(
            xAxisData = xGraphData,//listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
            //    GraphData.String(it)
            //}, // xAxisData : List<GraphData>, and GraphData accepts both Number and String types
            yAxisData = yData, //listOf(200, 40, 60, 450, 700, 30, 50),
            header = {
                Text(text = "Roll pitch yaw", fontSize = 40.sp, color = Color.Yellow)
            },
            style = graphStyle,
            isPointValuesVisible = true
        )
    }
}

@Preview
@Composable
fun GraphTestPreview() {
    AirMockApiAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            GraphTest(
                xData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"),
                yData = listOf(200, 40, 60, 450, 700, 30, 50),
                modifier = Modifier.fillMaxWidth(),
                title = "title",
                graphStyle = LineGraphStyle(colors = LinearGraphColors())
            )

        }
    }
}