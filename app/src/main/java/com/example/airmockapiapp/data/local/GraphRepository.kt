package com.example.airmockapiapp.data.local

import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.model.ColorData
import com.example.airmockapiapp.data.model.GraphResponse
import com.example.airmockapiapp.data.remote.GraphApi
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GraphRepository() {
    suspend fun getGraphData(): Response<List<GraphResponse>> {
        return api.getGraphData()
    }
    suspend fun postLEDcolor(colorData: ColorData): Response<ResponseBody> {
        return api.postLEDcolor(colorData)
    }

    companion object {
        val api: GraphApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GraphApi::class.java)
    }
}