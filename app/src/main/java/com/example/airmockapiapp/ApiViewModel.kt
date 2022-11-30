package com.example.airmockapiapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
                Log.d("API", "Response: ${response.body()?.string()}")
                _responseState.value = response.body()?.string()
            } else {
                _responseState.value = "Api call unsuccessful, error: ${response.code()}"
            }
        }
    }

}