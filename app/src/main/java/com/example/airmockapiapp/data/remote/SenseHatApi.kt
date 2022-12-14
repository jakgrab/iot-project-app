package com.example.airmockapiapp.data.remote

import com.example.airmockapiapp.data.model.GraphResponse
import com.example.airmockapiapp.data.model.SensorData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface SenseHatApi {
    @GET("/graph")
    suspend fun getGraphData(): Response<List<GraphResponse>>

    @Headers("Accept: application/json")
    @POST("/color")
    suspend fun postLEDcolor(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/sensors")
    suspend fun getSensorData(): Response<SensorData>

}