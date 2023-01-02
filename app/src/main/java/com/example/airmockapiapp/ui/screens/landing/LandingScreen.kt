package com.example.airmockapiapp.ui.screens.landing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.airmockapiapp.ui.navigation.AirScreens

@Composable
fun LandingScreen(navController: NavController) {
    val modifier: Modifier = Modifier
        .fillMaxWidth(0.6f)
        .height(60.dp)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Raspberry Pi SenseHat Control", fontSize = 25.sp, textAlign = TextAlign.Center)
            Text(text = "Choose Destination: ", fontSize = 23.sp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DestinationButton(modifier = modifier, buttonText = "LED's Control") {
                    navController.navigate(AirScreens.LedScreen.name)
                }

                Spacer(modifier = Modifier.height(50.dp))

                DestinationButton(modifier = modifier, buttonText = "Chart") {
                    navController.navigate(AirScreens.GraphScreen.name)
                }

                Spacer(modifier = Modifier.height(50.dp))

                DestinationButton(modifier = modifier, buttonText = "Sensors") {
                    navController.navigate(AirScreens.SensorScreen.name)
                }
            }

        }
    }
}

@Composable
private fun DestinationButton(
    modifier: Modifier = Modifier,
    buttonText: String = "Screen",
    onDestinationClicked: () -> Unit = {}
) {
    OutlinedButton(
        onClick = { onDestinationClicked() },
        modifier = modifier,
        elevation = ButtonDefaults.elevation(defaultElevation = 6.dp, pressedElevation = 10.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = buttonText, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
    }
}

//@Preview
//@Composable
//fun LandingScreenPreview() {
//    AirMockApiAppTheme {
//        LandingScreen()
//    }
//}