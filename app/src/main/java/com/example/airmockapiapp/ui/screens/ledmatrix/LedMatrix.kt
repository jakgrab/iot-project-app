package com.example.airmockapiapp.ui.screens.ledmatrix

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.airmockapiapp.ui.navigation.AirScreens
import com.github.skydoves.colorpicker.compose.*


@Composable
fun LedMatrix(ledViewModel: LedScreenViewModel, navController: NavHostController) {

    val listOfColors = MutableList(64) { Color.White }

    val indexList = mutableListOf<Int>()

    val indexListState = remember {
        mutableStateListOf<Int>()
    }
    val controller = rememberColorPickerController()

    val currentIndex = remember {
        mutableStateOf<Int?>(null)
    }

    val isColorPickerVisible = remember {
        mutableStateOf(false)
    }

//    val indexListTest = remember(indexList) {
//        mutableStateOf<List>(indexList)
//    }
    val isColorPicked = remember {
        mutableStateOf(false)
    }

    val colorState = remember {
        mutableStateOf<List<Color>>(listOfColors)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
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
                .padding(it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                itemsIndexed(listOfColors) { index, _ ->

                    Led(color = colorState.value[index]) {
                        indexListState.add(index)
                        Log.d("tag", "Added index: $index")
                        isColorPickerVisible.value = true
                        currentIndex.value = index
                    }
                }
            }
            AnimatedVisibility(visible = isColorPickerVisible.value) {
                ColorPicker(
                    ledViewModel = ledViewModel,
                    controller = controller,
                    colorState = colorState,
                    isColorPickerVisible = isColorPickerVisible,
                    isColorPicked = isColorPicked,
                    indexListState = indexListState
                    //indexList = indexList
                )
            }

        }
    }
}

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    ledViewModel: LedScreenViewModel,
    controller: ColorPickerController,
    colorState: MutableState<List<Color>>,
    isColorPicked: MutableState<Boolean>,
    isColorPickerVisible: MutableState<Boolean>,
    indexListState: SnapshotStateList<Int>
    //indexList: MutableList<Int>
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HsvColorPicker(
            modifier = modifier.height(200.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->

                indexListState.forEach { ledIndex ->
                    Log.d("tag", "Index: $ledIndex")
                    colorState.value = colorState.value.toMutableList().apply {
                        this[ledIndex] = colorEnvelope.color
                    }
                }
            }
        )

        AlphaTile(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp)),
            controller = controller
        )

        Button(
            onClick = {
                isColorPicked.value = true
//                colorState.value.forEach {
//                    Log.d("tag", "Color state list element: $it")
//                }
                ledViewModel.postLedColors(
                    indexList = indexListState,
                    colorList = colorState.value
//                    ColorData(
//                        index = currentIndex.value!!,
//                        color = colorState.value[currentIndex.value!!].toString()
//                    )
                )
                isColorPickerVisible.value = false
                //indexList.clear()
                indexListState.clear()
            }
        ) {
            Text(text = "Done")
        }
    }
}

@Composable
fun Led(
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
