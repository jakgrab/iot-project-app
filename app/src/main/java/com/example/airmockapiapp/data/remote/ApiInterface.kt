package com.example.airmockapiapp.data.remote

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    // changed Response<List<USer>> to Response<String>
    @GET(".")
    suspend fun getData(): Response<ResponseBody>
}