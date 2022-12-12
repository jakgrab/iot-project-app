package com.example.airmockapiapp.ui.screens.ledmatrix

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.airmockapiapp.data.local.AirRepository
import com.example.airmockapiapp.data.model.ColorData
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LedScreenViewModel: ViewModel() {
    private val repository = AirRepository()

    fun postLedColors(colorData: ColorData) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("index", colorData.index)
        jsonObject.addProperty("color", colorData.color)
        val jsonObjectToString = jsonObject.toString()

        val requestBody = jsonObjectToString.toRequestBody("application/json".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO){
            val response = repository.postLEDcolor(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("tag", "Upload successful, message: ${response.message()}")
                } else {
                    Log.d("tag", "Upload failed, message: ${response.body()}")
                }
            }
        }
    }
}