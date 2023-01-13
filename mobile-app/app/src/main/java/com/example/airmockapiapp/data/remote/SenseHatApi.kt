package com.example.airmockapiapp.data.remote

import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.ColorStatus
import com.example.airmockapiapp.data.model.SensorData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface SenseHatApi {
    // TODO joystick value may not be needed
    @GET("/")
    suspend fun getSensorData(
        @Query("temperature") temperature: String = "c",
        @Query("humidity") humidity: String = "%",
        @Query("pressure") pressure: String = "hpa",
        @Query("orientation") orientation: String = "d",
        @Query("joystick") joystick: String = ""
    ): Response<SensorData>

    @Headers("Accept: application/json")
    @POST("/led")
    suspend fun postLedColors(@Body colorDataList: List<ColorData>): Response<ResponseBody>

    @GET("/")
    suspend fun getLedColors(): Response<ColorStatus>
}