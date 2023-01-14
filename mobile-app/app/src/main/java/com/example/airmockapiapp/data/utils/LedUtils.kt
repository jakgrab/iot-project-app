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

        Log.d("Led Utils", "indexList: $indexList, colorList: $colorList")
        return ColorData(
            requests = indexList.zip(colorList) { index, color ->
                Log.d("color", "Color: $color")
                LedParams(indexToPositionList(index), colorToRgbList(color))
            }
        )
    }

    fun convertToColors(diodeList: List<List<Float>>): MutableList<Color> {
        return diodeList
            .map { Color(red = it[0].toInt(), green = it[1].toInt(), blue = it[2].toInt()) }
            .toMutableList()

    }

    private fun indexToPositionList(index: Int): List<Int> {
        val row = index / 8
        val column = index % 8
        Log.d("position", "row: $column, column: $row")
        return listOf(row, column)
    }

    private fun colorToRgbList(color: Color): List<Int> {
        Log.d(
            "color", "colorToRgbList: \n " +
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