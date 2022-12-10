package com.example.airmockapiapp.data.local

import com.example.airmockapiapp.data.Constants
import com.example.airmockapiapp.data.model.GraphResponse
import com.example.airmockapiapp.data.remote.GraphApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GraphRepository() {
    suspend fun getGraphData(): Response<GraphResponse> {
        return api.getGraphData()
    }

    companion object {
        val api: GraphApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GraphApi::class.java)
    }
}