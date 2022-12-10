package com.example.airmockapiapp

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResponseField(
    responseState: State<String?>,
) {

    if (responseState.value == null)
        Box(modifier = Modifier.fillMaxSize()) {

        }
        Log.d("API", "RESPONSE FIELD: response = $responseState")
        Card(
            modifier = Modifier
                .size(
                    width = 350.dp,
                    height = 300.dp
                ),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = Color(0xFF1F1843),
            border = BorderStroke(1.dp, Color(0xFF216430)),
            elevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = "Api Response: ${responseState.value}",
                    color = Color.White
                )

            }
        }

}