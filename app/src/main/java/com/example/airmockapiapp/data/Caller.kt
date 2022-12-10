package com.example.airmockapiapp.data

import com.example.airmockapiapp.data.model.GraphResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Caller {
    fun call(delay: Long, query: String): Flow<Response<GraphResponse>>
}