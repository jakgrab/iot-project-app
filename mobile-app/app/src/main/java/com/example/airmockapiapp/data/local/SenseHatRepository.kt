package com.example.airmockapiapp.data.local

import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.LedParams
import com.example.airmockapiapp.data.model.ColorStatus
import com.example.airmockapiapp.data.model.SensorData
import com.example.airmockapiapp.data.remote.SenseHatApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SenseHatRepository {
    suspend fun getSensorData(
        temperature: String,
        humidity: String,
        pressure: String,
        orientation: String,
        joystick: String
    ): Response<SensorData> {
        return api.getSensorData(
            temperature,
            humidity,
            pressure,
            orientation,
            joystick
        )
    }

    suspend fun postLedColors(colorDataList: ColorData): Response<ResponseBody> {
        return api.postLedColors(colorDataList)
    }

    suspend fun getLedColors(): Response<ColorStatus> {
        return api.getLedColors()
    }

    companion object {
        val api: SenseHatApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SenseHatApi::class.java)
    }
}