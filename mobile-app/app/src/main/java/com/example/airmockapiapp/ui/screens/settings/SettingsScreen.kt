package com.example.airmockapiapp.ui.screens.settings

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.airmockapiapp.ui.screens.MainViewModel
import com.example.airmockapiapp.ui.theme.AirMockApiAppTheme

@Composable
fun SettingsScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    var currentSamplingRate = mainViewModel.ddelay

    var expanded by remember {
        mutableStateOf(false)
    }
    val samplingRates = arrayOf(0.5f, 0.7f, 1f, 1.5f, 3f)

    var chosenRate by remember {
        mutableStateOf(mainViewModel.ddelay)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = "Options")},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack()}//navController.navigate(AirScreens.Settings.name) }
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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .padding(top = 80.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(text = "Change Sampling Rate", fontSize = 20.sp)
//                    SamplingRateDropdown(chosenRate, expanded) {
//                        expanded = !expanded
//                    }
//                }
//                AnimatedVisibility(visible = expanded) {
//                    SampleList(samplingRates = samplingRates) { rate ->
//                        chosenRate = rate
//                    }
//                }\
                ChangeSamplingRate(
                    samplingRates = samplingRates,
                    chosenRate = chosenRate,
                    expanded = expanded,
                    onExpanded = { expanded = !expanded },
                    onRateChosen = { rate ->
                        chosenRate = rate
                        mainViewModel.ddelay = rate
                        Log.d("tag", "Chosen rate update: mainViewModel.ddelay = $rate")
                    }
                )
            }
        }
    }
}

@Composable
fun ChangeSamplingRate(
    samplingRates: Array<Float>,
    chosenRate: Float,
    expanded: Boolean,
    onExpanded: () -> Unit,
    onRateChosen: (Float) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Change Sampling Rate", fontSize = 20.sp)
        SamplingRateDropdown(chosenRate, expanded) {
            onExpanded()
        }
    }
    AnimatedVisibility(visible = expanded) {
        SampleList(samplingRates = samplingRates) { rate ->
            onRateChosen(rate)
        }
    }

}

@Composable
fun SamplingRateDropdown(chosenRate: Float, expanded: Boolean, onArrowIconClicked: () -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$chosenRate s",
            fontSize = 20.sp
        )
        IconButton(
            onClick = {
                Log.d("DD", "Clicked")
                onArrowIconClicked()
            }
        ) {
            Icon(
                imageVector = if (!expanded)
                    Icons.Rounded.ArrowDropDown
                else Icons.Rounded.KeyboardArrowUp,
                contentDescription = "choose sampling rate",
                modifier = Modifier.size(50.dp)
            )
        }
    }

}

@Composable
fun SampleList(samplingRates: Array<Float>, onChosenRate: (Float) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Card(border = BorderStroke(width = 1.dp, color = Color.LightGray)) {
            LazyColumn(
                modifier = Modifier.width(100.dp)
            ) {
                items(samplingRates) { rate ->
                    SamplingRateItem(rate) { chosenRate ->
                        onChosenRate(chosenRate)
                    }
                }
            }
        }
    }
}

@Composable
fun SamplingRateItem(rate: Float, onRateClicked: (Float) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.d("DD", "Rate: $rate")
                onRateClicked(rate)
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "$rate s", fontSize = 20.sp)
    }
}

@Preview
@Composable
fun SettingsPreview() {
    AirMockApiAppTheme {

    }
}