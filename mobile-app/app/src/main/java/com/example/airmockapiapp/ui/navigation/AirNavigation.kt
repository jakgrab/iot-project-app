package com.example.airmockapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.airmockapiapp.ui.screens.graph.GraphViewModel
import com.example.airmockapiapp.ui.screens.graph.GraphScreen
import com.example.airmockapiapp.ui.screens.landing.LandingScreen
import com.example.airmockapiapp.ui.screens.ledmatrix.LedMatrix
import com.example.airmockapiapp.ui.screens.ledmatrix.LedScreenViewModel
import com.example.airmockapiapp.ui.screens.sensor.SensorScreen
import com.example.airmockapiapp.ui.screens.sensor.SensorViewModel

@Composable
fun AirNavigation() {

    val navController = rememberNavController()
    val graphViewModel: GraphViewModel = viewModel()
    val sensorViewModel: SensorViewModel = viewModel()
    val ledViewModel: LedScreenViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AirScreens.LandingScreen.name
    ) {
        composable(AirScreens.LandingScreen.name) {
            LandingScreen(navController)
        }
        composable(AirScreens.GraphScreen.name) {
            GraphScreen(graphViewModel, navController)
        }
        composable(AirScreens.LedScreen.name) {
            LedMatrix(ledViewModel, navController)
        }
        composable(AirScreens.SensorScreen.name) {
            SensorScreen(sensorViewModel, navController)
        }
    }
}