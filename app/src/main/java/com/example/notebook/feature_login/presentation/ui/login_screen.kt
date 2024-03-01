package com.example.notebook.feature_login.presentation.ui

import androidx.compose.foundation.layout.*

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.feature_login.presentation.components.*


@Composable
fun LoginScreen(){

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                NormalTextComponent(
                    value = stringResource(id = R.string.Hi),
                )

                HeadingTextComponent(
                    value = stringResource(id = R.string.create_account),
                )

                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_email),
                    painterResource(id = R.drawable.message),
                    { },
                    true
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_pass),
                    painterResource(id = R.drawable.ic_lock)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoginButton("Login")
            }
        }
    }

}

@Preview
@Composable
fun LoginScreenPreview(){
    MaterialTheme{

        LoginScreen()
    }

}
