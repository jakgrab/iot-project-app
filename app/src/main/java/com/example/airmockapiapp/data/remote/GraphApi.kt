package com.example.airmockapiapp.data.remote

import com.example.airmockapiapp.data.model.GraphResponse
import retrofit2.Response
import retrofit2.http.GET

interface GraphApi {
    @GET("/graph")
    suspend fun getGraphData(): Response<GraphResponse>
//    @GET(".")
//    suspend fun getData(): Response<ResponseBody>
}