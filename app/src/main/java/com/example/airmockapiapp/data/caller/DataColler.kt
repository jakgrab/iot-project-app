package com.example.airmockapiapp.data

import android.util.Log
import com.example.airmockapiapp.data.caller.CallState
import com.example.airmockapiapp.data.caller.GraphCaller
import com.example.airmockapiapp.data.local.AirRepository
import com.example.airmockapiapp.data.model.GraphResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class GraphDataCaller(
    //private val viewModel: ApiViewModel,
    private val callingState: StateFlow<CallState>,
    private val repository: AirRepository
): GraphCaller {

    // Returns channelFlow which calls API in a loop with a delay
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun callGraphData(delay: Long, query: String): Flow<Response<List<GraphResponse>>> {

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