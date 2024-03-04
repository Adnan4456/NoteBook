package com.example.notebook.feature_signup.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.components.*
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_note.presentation.util.Screen

import com.example.notebook.feature_signup.domain.model.SignUpResult
import com.example.notebook.ui.theme.Primary
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
){

    val networkStatus by  viewModel.networkStatus.collectAsState()
    val signupState by viewModel.signupState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


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
            color = MaterialTheme.colorScheme.surface
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

                EmailTextFieldComponent(
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
                LoginButton(
                    "Signup" ,
                    isEnabled = true,
                    onClick = {
//                    navController.navigate(Screen.NotesScreen.route)
                        //firs check internet connection
                        when (networkStatus) {
                            ConnectivityObserver.Status.Available -> {

                                viewModel.onEvent(RegistrationFormEvent.Submit)
                            }
                            else -> {
                                // Show Snackbar if the network is not available
                                coroutineScope.launch{

                                    snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.not_connected),
                                        actionLabel = context.getString(R.string.dismiss)
                                    )
                                }

                            }
                        }
                    })
                when (signupState) {
                    is SignUpResult.isLoading -> {
                        showDialog = true
                        LoadingDialogBox(
                            showDialog = showDialog,
                            onDismiss = {
                                showDialog = false
                            }
                        )
                    }
                    is SignUpResult.isSuccessful -> {
                        Toast.makeText(LocalContext.current ,"SignUp in successfully", Toast.LENGTH_SHORT).show()
                        showDialog = false
                        navController.navigate(Screen.NotesScreen.route)
                    }
                    is SignUpResult.onFailure -> {
                        // Login failed, show error message
                        val errorMessage = (signupState as SignUpResult.onFailure)
                        Toast.makeText(LocalContext.current ,errorMessage.message, Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }
                    else -> {}
                }
                Text(
                    buildAnnotatedString {

                        append("Already have account ? ")
                        withStyle(style =  SpanStyle(
                            color = Primary
                        )
                        ){
                            append(" Login ")
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    MaterialTheme{
//        SignUpScreen()
    }
}