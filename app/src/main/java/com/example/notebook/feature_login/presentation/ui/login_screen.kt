package com.example.notebook.feature_login.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.feature_login.presentation.components.MyTextFieldComponent
import com.example.notebook.feature_login.presentation.components.TextFields


@Composable
fun LoginScreen(){

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .clip(RoundedCornerShape(4.dp)
                ),
            horizontalAlignment = Alignment.Start
        ) {

            TextFields(
                value = stringResource(id = R.string.Hi),
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)

            TextFields(
                value = stringResource(id = R.string.create_account),
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.label_email)
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}