package com.example.airmockapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.airmockapiapp.ui.screens.MainViewModel
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
    val mainViewModel: MainViewModel = hiltViewModel()
//    val graphViewModel: GraphViewModel = viewModel()
//    val sensorViewModel: SensorViewModel = viewModel()
//    val ledViewModel: LedScreenViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AirScreens.LandingScreen.name
    ) {
        composable(AirScreens.LandingScreen.name) {
            LandingScreen(mainViewModel, navController)
        }
        composable(AirScreens.GraphScreen.name) {
            GraphScreen(mainViewModel, navController)
        }
        composable(AirScreens.LedScreen.name) {
            LedMatrix(mainViewModel, navController)
        }
        composable(AirScreens.SensorScreen.name) {
            SensorScreen(mainViewModel, navController)
        }
    }
}