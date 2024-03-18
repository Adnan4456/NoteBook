package com.example.notebook.feature_forget_password.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.components.EmailTextFieldComponent
import com.example.notebook.components.LoadingDialogBox
import com.example.notebook.components.LoginButton
import com.example.notebook.components.NormalTextComponent
import com.example.notebook.feature_forget_password.data.model.SendEmailResponse
import com.example.notebook.feature_forget_password.presentation.ForgetPasswordEvent


@Composable
fun ForgetScreen (
    navController: NavController,
    viewModel: ForgetPasswordViewModel = hiltViewModel()
){


    val responseState by viewModel.responseState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }

//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current

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
                    .fillMaxWidth()
                    .fillMaxHeight(.6f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Spacer(modifier = Modifier.height(16.dp))
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

                Spacer(modifier = Modifier.height(34.dp))

                Text(
                    text = "Please enter your account email address. we will send you password reset email",
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    EmailTextFieldComponent(
                        labelValue = stringResource(id = R.string.label_email),
                        painterResource(id = R.drawable.message),
                        onTextChanged = {email ->
                            viewModel.onEvent(ForgetPasswordEvent.EmailChanged(email))
                        },
                        errorStatus = !viewModel.state.emailError
                    )
                    if(viewModel.state.emailError){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.End
                        ){

                            Text(viewModel.state.emailErrorMessage.toString(),
                                style = TextStyle(
                                    color = Color.Red
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    LoginButton(
                        "Submit" ,
                        isEnabled = true,
                        onClick = {
                            viewModel.onEvent(ForgetPasswordEvent.submit)
                        })

                    when(responseState){

                        is SendEmailResponse.isLoading ->{
                            showDialog = true
                            LoadingDialogBox(
                                showDialog = showDialog,
                                onDismiss = {
                                    showDialog = false
                                }
                            )
                        }
                        is SendEmailResponse.isSuccessfull ->{
                            Toast.makeText(LocalContext.current ,"Email send successfully.Please update you password", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        }
                        is SendEmailResponse.onFailure ->{
                            Toast.makeText(LocalContext.current ,"Error in send mail.Please try again",Toast.LENGTH_SHORT).show()
                            showDialog = false
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}