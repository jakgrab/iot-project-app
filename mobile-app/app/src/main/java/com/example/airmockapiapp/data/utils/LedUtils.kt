package com.example.airmockapiapp.data.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.LedParams

class LedUtils {

    fun toColorData(
        indexList: List<Int>,
        colorList: List<Color>
    ): ColorData {

        val filteredColors = filterColorsByIndex(indexList, colorList)
        Log.d("LED", "indexList: $indexList, colorList: $filteredColors")
        return ColorData(
            requests = indexList.zip(filteredColors) { index, color ->
                Log.d("LED", "Color: $color")
                LedParams(indexToPositionList(index), colorToRgbList(color))
            }
        )
    }

    fun convertToColors(diodeList: List<List<Float>>): MutableList<Color> {
        return diodeList
            .map { Color(red = it[0].toInt(), green = it[1].toInt(), blue = it[2].toInt()) }
            .toMutableList()

    }

    private fun filterColorsByIndex(indexList: List<Int>, colorList: List<Color>): List<Color> {
        return colorList.filterIndexed { index, _ -> indexList.contains(index) }
    }

    private fun indexToPositionList(index: Int): List<Int> {
        val row = index / 8
        val column = index % 8
        Log.d("LED", "row: $column, column: $row")
        return listOf(column, row)
    }

    private fun colorToRgbList(color: Color): List<Int> {
        Log.d(
            "LED", "colorToRgbList: \n " +
                    "red: ${(color.red * 255).toInt()}, \n" +
                    "green: ${(color.green * 255).toInt()}, \n" +
                    "blue: ${(color.blue * 255).toInt()}"
        )

        return listOf(
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
    }


}