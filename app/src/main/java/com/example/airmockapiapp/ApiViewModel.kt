package com.example.airmockapiapp

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call

class ApiViewModel: ViewModel() {

    private val _responseState = MutableStateFlow<String?>(null)
    val responseState = _responseState.asStateFlow()

    fun getData(apiInterface: ApiInterface) {

        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getData()

            if (response.isSuccessful) {

                //Log.d("API", "Response: ${response.body()?.string()}")
                // NOTE: Somehow response.body.string can be only called once

                val result = response.body()?.string()
                if (result != null) {
                    _responseState.emit(result)
                }
                Log.d("API", "responseState.value = ${_responseState.value}")
            } else {
                _responseState.value = "Api call unsuccessful, error: ${response.code()}"
            }
        }
    }

}