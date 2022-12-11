package com.example.airmockapiapp.data.remote

import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.GraphResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GraphApi {
    @GET("/graph")
    suspend fun getGraphData(): Response<List<GraphResponse>>

    @POST("/color")
    suspend fun postLEDcolor(@Body data: ColorData): Response<ResponseBody>

}