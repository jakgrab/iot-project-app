package com.example.airmockapiapp.data

import android.util.Log
import androidx.compose.runtime.State
import com.example.airmockapiapp.data.local.GraphRepository
import com.example.airmockapiapp.data.model.GraphResponse
import com.example.airmockapiapp.ui.screens.ApiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class DataCaller(
    //private val viewModel: ApiViewModel,
    private val callingState: StateFlow<CallState>,
    private val repository: GraphRepository
): Caller {

    // Returns channelFlow which calls API in a loop with a delay
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun call(delay: Long, query: String): Flow<Response<GraphResponse>> {

        return channelFlow {
            while (!isClosedForSend) {
                if (callingState.value == CallState.INACTIVE) {
                    Log.d("Tag", "CALLER STOPPING")
                    close()
                    return@channelFlow
                }
                Log.d("Tag", "CALLER RUNNING")
                delay(delay)
                send(repository.getGraphData())
            }
        }.flowOn(Dispatchers.IO)
    }
}