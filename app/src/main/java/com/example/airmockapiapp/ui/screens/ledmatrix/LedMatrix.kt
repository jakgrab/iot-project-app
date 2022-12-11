package com.example.airmockapiapp.ui.screens.ledmatrix

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.*

@Preview
@Composable
fun LedMatrix() {
    val colorList = List(64) { Color.White }

    val nwm = MutableList(64) { Color.White }

    val controller = rememberColorPickerController()

    val currentIndex = remember {
        mutableStateOf<Int?>(null)
    }

    val isColorPickerVisible = remember {
        mutableStateOf(false)
    }
    val isColorPicked = remember {
        mutableStateOf(false)
    }
    val pickedColor = remember() { //no isColor... b4
        // no derived b4
        mutableStateOf(Color.White)
    }
    val colorState = remember() { //colorList
        mutableStateOf<List<Color>>(nwm)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isColorPickerVisible.value) {
            isColorPicked.value = false
            LazyVerticalGrid(
                columns = GridCells.Fixed(8),
                modifier = Modifier,
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                itemsIndexed(nwm) { index, item ->
//                    colorState.value = colorState.value.toMutableList().apply {
//                        this[index] = pickedColor.value
//                    }
                    Led(color = colorState.value[index]) {
                        isColorPickerVisible.value = true
                        //nwm[index] = pickedColor.value

                        currentIndex.value = index
                        //if (isColorPicked.value) { // no condition b4
//                        colorState.value = colorState.value.toMutableList().apply {
//
//                                this[index] = pickedColor.value
//                            // no double value b4
//                        }
                        Log.d("tag", "Changing color")

                        //}
                        Log.d("tag", "Color state value ${colorState.value[index]}")

                    }
                }
            }
        } else {
            //isColorPicked.value = false// ???????
            Log.d("tag", "Showing color picker")
            ColorPicker(
                controller = controller,
                // no .value b4
                color = pickedColor,
                currentIndex = currentIndex,
                colorState = colorState,
                isColorPickerVisible = isColorPickerVisible,
                isColorPicked = isColorPicked
            )
            Log.d("tag", "picked color: ${pickedColor.value}")
        }
    }
}

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    controller: ColorPickerController,
    color: MutableState<Color>,
    currentIndex: MutableState<Int?>,
    colorState:  MutableState<List<Color>>,
    isColorPicked: MutableState<Boolean>,
    isColorPickerVisible: MutableState<Boolean>
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        HsvColorPicker(
            modifier = modifier.height(300.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                if (currentIndex.value != null) {
                    colorState.value = colorState.value.toMutableList().apply {
                        this[currentIndex.value!!] = colorEnvelope.color
                    }
                    //color.value = colorEnvelope.color testing
                }
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        AlphaTile(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp)),
            controller = controller
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                isColorPicked.value = true
                isColorPickerVisible.value = false
                Log.d("tag", "Button: isColorPicked: ${isColorPicked.value}")
            }
        ) {
            Text(text = "Done")
        }
    }
}

@Composable
fun Led(
    //index: Int,
    color: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Card(
        backgroundColor = color,
        modifier = Modifier
            .padding(5.dp)
            .height(40.dp)
            .width(50.dp)
            .clickable {
                onClick()
            },
        border = BorderStroke(width = 0.5.dp, color = Color(0xFFC9C9C9)),
        elevation = 8.dp
    ) {}
}

@Composable
fun Test(onClick: () -> Unit, content: @Composable () -> Unit = {}) {

    Card(modifier= Modifier
        .padding(5.dp)
        .height(40.dp)
        .width(50.dp)
        .clickable { onClick() }) {
        content()
    }

}