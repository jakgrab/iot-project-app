package com.example.airmockapiapp.ui.screens.sensor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.model.SensorData
import com.example.airmockapiapp.ui.screens.MainViewModel


@Composable
fun SensorScreen(viewModel: MainViewModel, navController: NavController) {

    val sensorData = viewModel.sensorData.collectAsState()

    val sensorDataState = remember(sensorData.value) {
        mutableStateOf(sensorData.value)
    }

    val callingState = viewModel.callingState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
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
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.DarkGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Weather Condition", fontSize = 20.sp)

                WeatherConditions(data = sensorDataState.value, fontSize = 16.sp)

                Divider(modifier = Modifier.fillMaxWidth(0.8f), color = Color.Gray)
                Text(text = "Orientation", fontSize = 20.sp)

                Orientation(data = sensorDataState.value)

                Divider(modifier = Modifier.fillMaxWidth(0.8f), color = Color.Gray)
                Text(text = "Joystick", fontSize = 20.sp)

                JoystickMovement(data = sensorDataState.value)

            }
            AnimatedVisibility(visible = callingState.value == CallState.INACTIVE) {
                Button(onClick = {
                    viewModel.resumeDataStream()
                }) {
                    Text(text = "Start")
                }
            }
            AnimatedVisibility(visible = callingState.value == CallState.ACTIVE) {
                if (callingState.value == CallState.ACTIVE) {
                    Button(onClick = {
                        viewModel.cancelDataStream()
                    }) {
                        Text(text = "Stop data stream")
                    }
                }
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}

@Composable
private fun WeatherConditions(data: SensorData?, fontSize: TextUnit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Temp.:", fontSize = fontSize)
            Spacer(modifier = Modifier.width(5.dp))

            if (data != null) {
                Text(text = data.temperature.toString(), fontSize = fontSize)
            }
            Text(text = "°", fontSize = fontSize)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Press.:", fontSize = fontSize)
            Spacer(modifier = Modifier.width(5.dp))
            if (data != null) {
                Text(text = data.pressure.toString(), fontSize = fontSize)
            }
            Text(text = "hPa", fontSize = fontSize)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Hum.:", fontSize = fontSize)
            Spacer(modifier = Modifier.width(5.dp))
            if (data != null) {
                Text(text = data.humidity.toString(), fontSize = fontSize)
            }

            Text(text = "%", fontSize = fontSize)
        }
    }
}

@Composable
private fun Orientation(data: SensorData?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Roll:")
            if (data != null) {
                Text(text = data.orientation[0].toString())
            }
            Text(text = "°")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pitch:")
            if (data != null) {
                Text(text = data.orientation[1].toString())
            }
            Text(text = "°")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Yaw.:")
            if (data != null) {
                Text(text = data.orientation[2].toString())
            }
            Text(text = "°")
        }
    }
}

@Composable
private fun JoystickMovement(data: SensorData?) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Horizontal position:")
            if (data != null) {
                Text(text = data.joystickPosition[0].toString())
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Vertical position:")
            if (data != null) {
                Text(text = data.joystickPosition[1].toString())
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pressed:")
            if (data != null) {
                Text(text = data.joystickClicks.toString())
            }

        }

    }
}
