package com.example.airmockapiapp.data.utils

import androidx.compose.ui.graphics.Color
import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.LedParams

class LedUtils {

    fun toColorData(
        indexList: List<Int>,
        colorList: List<Color>
    ): ColorData {

        val filteredColors = filterColorsByIndex(indexList, colorList)

        return ColorData(
            requests = indexList.zip(filteredColors) { index, color ->
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
        return listOf(column, row)
    }

    private fun colorToRgbList(color: Color): List<Int> {
        return listOf(
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
    }


}