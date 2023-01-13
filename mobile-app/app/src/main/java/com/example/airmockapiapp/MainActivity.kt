package com.example.airmockapiapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.airmockapiapp.ui.navigation.AirNavigation
import com.example.airmockapiapp.ui.navigation.AirScreens
import com.example.airmockapiapp.ui.theme.AirMockApiAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirMockApiAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Counter()
                    AirNavigation()
                }
            }
        }
    }
}


@Composable
fun Counter() {
    val count = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                count.value += "click"
                Log.d("f", "Value: ${count.value}")
                Toast.makeText(context, "Clicked button ${count.value}", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("Click me")
        }
        Text(text = "Current count: ${count.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AirMockApiAppTheme {
        //MainScreen()
    }
}