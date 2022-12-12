package com.example.airmockapiapp.ui.screens.sensor

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.model.SensorData
import com.example.airmockapiapp.ui.theme.AirMockApiAppTheme


@Composable
fun SensorScreen(viewModel: SensorViewModel, navController: NavController) {

    val sensorData = viewModel.sensorData.collectAsState()

    val sensorDataState = remember(sensorData.value) {
        mutableStateOf(sensorData.value)
    }

    val callingState = viewModel.callingState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
        Button(onClick = {
            if (callingState.value == CallState.INACTIVE) {
                viewModel.resumeDataStream()
            }
            viewModel.getSensorData()
        }) {
            Text(text = "Start")
        }
        if (callingState.value == CallState.ACTIVE) {
            Button(onClick = {
                viewModel.cancelDataStream()
            }) {
                Text(text = "Stop data stream")
            }
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}

@Composable
private fun WeatherConditions(data: SensorData?, fontSize: TextUnit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Temp.:", fontSize = fontSize)
            Spacer(modifier = Modifier.width(5.dp))

            if (data != null) {
                Text(text = data.temp.toString(),fontSize = fontSize)
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
                Text(text = data.press.toString(),fontSize = fontSize)
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
                Text(text = data.hum.toString(),fontSize = fontSize)
            }

            Text(text = "%", fontSize = fontSize)
        }
    }
}

@Composable
private fun Orientation(data: SensorData?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Roll:")
            if (data != null) {
                Text(text = data.roll.toString())
            }
            Text(text = "°")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pitch:")
            if (data != null) {
                Text(text = data.pitch.toString())
            }
            Text(text = "°")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Yaw.:")
            if (data != null) {
                Text(text = data.yaw.toString())
            }
            Text(text = "°")
        }
    }
}

@Composable
private fun JoystickMovement(data: SensorData?) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Up:")
            if (data != null) {
                Text(text = data.joy_up)
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Down:")
            if (data != null) {
                Text(text = data.joy_down)
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Left.:")
            if (data != null) {
                Text(text = data.joy_left)
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Right.:")
            if (data != null) {
                Text(text = data.joy_right)
            }
        }
    }
}

@Preview
@Composable
fun SensorScreenPreview() {
    AirMockApiAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

            //SensorScreen()
        }
    }
}