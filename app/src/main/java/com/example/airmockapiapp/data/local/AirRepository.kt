package com.example.airmockapiapp.data.local

import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.model.GraphResponse
import com.example.airmockapiapp.data.model.SensorData
import com.example.airmockapiapp.data.remote.SenseHatApi
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AirRepository() {
    suspend fun getGraphData(): Response<List<GraphResponse>> {
        return api.getGraphData()
    }
    suspend fun postLedColors(requestBody: RequestBody): Response<ResponseBody> {
        return api.postLEDcolor(requestBody)
    }
    suspend fun getSensorData(): Response<SensorData> {
        return api.getSensorData()
    }

    companion object {
        val api: SenseHatApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SenseHatApi::class.java)
    }
}