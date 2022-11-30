package com.example.airmockapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.airmockapiapp.ui.theme.AirMockApiAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirMockApiAppTheme {
                // A surface container using the 'background' color from the theme

                    MainScreen()

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AirMockApiAppTheme {
        MainScreen()
    }
}