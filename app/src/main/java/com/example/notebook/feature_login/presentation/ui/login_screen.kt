package com.example.notebook.feature_login.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.feature_login.presentation.components.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){

    val networkStatus by  viewModel.networkStatus.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    if (networkStatus == ConnectivityObserver.Status.Available) {
        LaunchedEffect(networkStatus) {
            snackbarHostState.showSnackbar(
                message = when (networkStatus) {

                    ConnectivityObserver.Status.Unavailable -> "No internet connection."
                    ConnectivityObserver.Status.Available -> "Available network connection."
                    ConnectivityObserver.Status.Losing -> "Losing network connection."
                    ConnectivityObserver.Status.Lost -> "Lost network connection."
                },
                actionLabel = "Dismiss"
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            color = MaterialTheme.colors.surface
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

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
                    onTextChanged = {email ->
                    viewModel.onEvent(RegistrationFormEvent.EmailChanged(email))
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
                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_pass),
                    painterResource(id = R.drawable.ic_lock),
                    onTextChanged = {passsword ->
                        viewModel.onEvent(RegistrationFormEvent.PasswordChanged(passsword))
                    },
                    errorStatus = viewModel.state.passwordError
                )
                if(viewModel.state.passwordError){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ){

                        Text(viewModel.state.passwordErrorMessage.toString(),
                            style = TextStyle(
                                color = Color.Red
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(26.dp))
                LoginButton("Login" , onClick = {
//                    navController.navigate(Screen.NotesScreen.route)
                    //firs check internet connection
                    when (networkStatus) {
                        ConnectivityObserver.Status.Available -> {
                            GlobalScope.launch{

                                snackbarHostState.showSnackbar(
                                    message = "Connect to the internet.",
                                    actionLabel = "Dismiss"
                                )
                            }
                            viewModel.onEvent(RegistrationFormEvent.Submit)
                        }
                        else -> {
                            // Show Snackbar if the network is not available
                            GlobalScope.launch{

                                snackbarHostState.showSnackbar(
                                    message = "Connect to the internet first.",
                                    actionLabel = "Dismiss"
                                )
                            }

                        }
                    }
                })
            }
        }
    }
}



@Preview
@Composable
fun LoginScreenPreview(){
    MaterialTheme{
    }
}
