package com.example.airmockapiapp.data.local

import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.SensorBody
import com.example.airmockapiapp.data.model.SensorData
import com.example.airmockapiapp.data.remote.SenseHatApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SenseHatRepository {
    suspend fun getSensorData(body: SensorBody): Response<SensorData> {
        return api.getSensorData(body)
    }

    suspend fun postLedColors(colorDataList: List<ColorData>): Response<ResponseBody> {
        return api.postLEDcolor(colorDataList)
    }


    companion object {
        val api: SenseHatApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SenseHatApi::class.java)
    }
}