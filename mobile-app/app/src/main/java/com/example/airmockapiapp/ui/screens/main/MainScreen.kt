package com.example.airmockapiapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airmockapiapp.data.remote.ApiInterface
import com.example.airmockapiapp.ui.screens.graph.GraphViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun MainScreen() {

    val apiViewModel: GraphViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var api: ApiInterface?

    val textFieldValue = remember {
        mutableStateOf("")
    }

    Scaffold(scaffoldState = scaffoldState) {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFF261F4E)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val focusManager = LocalFocusManager.current

                UrlTextField(textFieldValue, focusManager)

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {
                        if (textFieldValue.value.isNotEmpty()) {
                            api = createRetrofitClient(textFieldValue)
                            if (api == null) {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Incorrect Url Address"
                                    )
                                }
                            } else {
                                //apiViewModel.getData(api!!)
                            }
                        }
                    },
                    modifier = Modifier.size(width = 180.dp, height = 50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF0CE93C),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Get API response",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                val responseState = apiViewModel.responseState.collectAsState()

                ResponseField(responseState)
            }
        }
    }
}

private fun createRetrofitClient(textFieldValue: MutableState<String>): ApiInterface? {
    val url =
        if (textFieldValue.value.endsWith("/"))
            textFieldValue.value
        else textFieldValue.value.plus("/")
    Log.d("API", "Create retrofit client from url: $url")
    return try {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

    } catch (e: IllegalArgumentException) {
        return null
    }
}

