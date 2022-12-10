package com.example.airmockapiapp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UrlTextField(textFieldValue: MutableState<String>, focusManager: FocusManager) {
    OutlinedTextField(
        value = textFieldValue.value,
        onValueChange = { input -> textFieldValue.value = input },
        modifier = Modifier
            .width(350.dp)
            .height(80.dp)
            .padding(horizontal = 10.dp),
        label = { Text(text = "Enter Api Url") },
        placeholder = { Text(text = "Api Url") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Gray,
            backgroundColor = Color(0xFF1F1843),
            cursorColor = Color(0xFF0CE93C),
            focusedBorderColor = Color(0xFF0CE93C),
            unfocusedBorderColor = Color(0xFF216430),
            leadingIconColor = Color(0xFF0CE93C),
            trailingIconColor = Color(0xFF0CE93C),
            focusedLabelColor = Color(0xFF216430),
            unfocusedLabelColor = Color.LightGray,
            placeholderColor = Color.LightGray
        )
    )
}