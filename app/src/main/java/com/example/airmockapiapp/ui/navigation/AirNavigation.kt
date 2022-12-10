package com.example.airmockapiapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.airmockapiapp.data.local.GraphRepository
import com.example.airmockapiapp.data.remote.GraphApi
import com.example.airmockapiapp.ui.screens.ApiViewModel
import com.example.airmockapiapp.ui.screens.graph.GraphScreen

@Composable
fun AirNavigation() {

    val navController = rememberNavController()
    val viewModel: ApiViewModel = viewModel()
    //val graphRepository = GraphRepository()

    NavHost(
        navController = navController,
        startDestination = AirScreens.GraphScreen.name
    ) {
        composable(AirScreens.GraphScreen.name) {
            GraphScreen(viewModel)
        }

    }


}