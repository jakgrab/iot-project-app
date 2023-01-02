package com.example.airmockapiapp.ui.screens.ledmatrix

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.local.AirRepository
import com.example.airmockapiapp.data.model.ColorData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LedScreenViewModel : ViewModel() {

    private val repository = AirRepository()

    fun postLedColors(
        indexList: List<Int>,
        colorList: List<Color>
    ) {
//        val colorDataList = convertToListOfColorData(indexList, colorList)
//        //val jsonObject = JsonObject()
//        val jsonArray = JsonArray()
//
//        colorDataList.forEach { colorData ->
//            val jsonObject = JsonObject()
//            jsonObject.addProperty("index", colorData.index)
//            jsonObject.addProperty("color", colorData.color)
//            jsonArray.add(jsonObject)
//        }
//
//        val jsonArrayToString = jsonArray.toString()
//        Log.d("tag", "JsonArray: $jsonArrayToString")
//
//        val requestBody = jsonArrayToString.toRequestBody("application/json".toMediaTypeOrNull())

        val testPosition = indexToPositionList(indexList.first())
        val testRgb = colorToRgbList(colorList.first())

        val testColorData = ColorData(position = testPosition, rgb = testRgb)

        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.postLedColors(requestBody)
            val response = repository.postLedColors(testColorData)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("tag", "Upload successful, message: ${response.message()}")
                } else {
                    Log.d("tag", "Upload failed, message: ${response.message()}")
                }
            }
        }
    }

//    private fun convertToListOfColorData(
//        indexList: List<Int>,
//        colorList: List<Color>,
//    ): List<ColorData> {
//        val colorDataList = mutableListOf<ColorData>()
//        indexList.forEach { index ->
//            colorDataList.add(ColorData(index, colorList[index].toString()))
//        }
//        return colorDataList
//    }

    private fun indexToPositionList(index: Int): List<Int> {
        val row = index / 8
        val column = index % 8
        return listOf(row, column)
    }

    private fun colorToRgbList(color: Color): List<Int> {
        return listOf(color.red.toInt(), color.green.toInt(), color.blue.toInt())
    }
}





