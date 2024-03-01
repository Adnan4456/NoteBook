package com.example.notebook.feature_login.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun TextFields(value: String , modifier: Modifier , textAlign: TextAlign){
    Text(
        modifier = modifier,
        text = value,
        textAlign = textAlign,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            color = Color.Black
        )
    )
}


@Composable
fun MyTextFieldComponent(labelValue: String){

    var textValue by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        leadingIcon = {
                      Icon(imageVector = Icons.Default.Email, contentDescription = "Email Address")
        },
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor =  Color(0xE6F3F5F5),
            focusedLabelColor = Color(0xE6F3F5F5),
            cursorColor = Color(0xE6F3F5F5),
        ),
        value = textValue,
        onValueChange = {
            textValue = it
        },
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth(),

    )
}