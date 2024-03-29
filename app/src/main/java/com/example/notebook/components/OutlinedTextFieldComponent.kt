package com.example.notebook.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.ui.theme.Primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextChanged: (String) -> Unit,

    errorStatus: Boolean = false
) {

    val textValue = rememberSaveable {
        mutableStateOf("")
    }
//    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.main_color),
            focusedLabelColor = colorResource(id = R.color.main_color),
            cursorColor = colorResource(id = R.color.main_color),
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(painter = painterResource,
                contentDescription = "",
            tint = colorResource(id = R.color.main_color)
            )
        },

        isError = !errorStatus
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    painterResource: Painter,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
){

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        label = { Text(text = labelValue) },
        maxLines = 1,
        leadingIcon =
        {
            Icon(painter = painterResource,
                contentDescription = labelValue,
                tint = colorResource(id = R.color.main_color)
            )
        },
        trailingIcon = {
            val iconImage =   if (passwordVisible){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            val decription = if (passwordVisible){
                stringResource(R.string.hide_password)
            }else
            {
                stringResource(R.string.show_password)
            }
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(imageVector =iconImage ,
                    contentDescription = decription,
                tint = colorResource(id = R.color.main_color))
            }
        },

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.main_color),
            focusedLabelColor = colorResource(id = R.color.main_color),
            cursorColor = colorResource(id = R.color.main_color),
//            backgroundColor = BgColor
        ),
        value = password,
        onValueChange = {
            password = it
            onTextChanged(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        isError = errorStatus
    )
}
