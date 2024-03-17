package com.example.notebook.feature_forget_password.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.components.EmailTextFieldComponent
import com.example.notebook.components.NormalTextComponent
import com.example.notebook.feature_login.presentation.ui.LoginFormEvent


@Composable
fun ForgetScreen (
    navController: NavController,
){

    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
        ){

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "",
                            tint = colorResource(id = R.color.main_color)
                        )
                    }
                    NormalTextComponent(
                        value = stringResource(id = R.string.forget_password),
                    )
                }


                //
                EmailTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_email),
                    painterResource(id = R.drawable.message),
                    onTextChanged = {email ->
//                        viewModel.onEvent(LoginFormEvent.EmailChanged(email))
                    },

                )
            }
        }
    }
}