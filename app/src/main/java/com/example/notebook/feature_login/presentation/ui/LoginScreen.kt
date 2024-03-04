package com.example.notebook.feature_login.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.notebook.components.*
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){

    val networkStatus by  viewModel.networkStatus.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

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
                actionLabel = context.getString(R.string.dismiss)
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
                
                IconButton(onClick = {

                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "" )
                }
                
                Spacer(modifier = Modifier.height(50.dp))

                NormalTextComponent(
                    value = stringResource(id = R.string.login),
                )

                HeadingTextComponent(
                    value = stringResource(id = R.string.create_account),
                )

                Spacer(modifier = Modifier.height(20.dp))

                EmailTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_email),
                    painterResource(id = R.drawable.message),
                    onTextChanged = {email ->
                    viewModel.onEvent(LoginFormEvent.EmailChanged(email))
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
                        viewModel.onEvent(LoginFormEvent.PasswordChanged(passsword))
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
                    "Login" ,
                    isEnabled = true,
                    onClick = {
                    when (networkStatus) {
                        ConnectivityObserver.Status.Available -> {
                            viewModel.onEvent(LoginFormEvent.Submit)
                        }
                        else -> {
                            coroutineScope.launch{
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.not_connected),
                                    actionLabel = context.getString(R.string.dismiss)
                                )
                            }
                        }
                    }
                })
                when (loginState) {
                    is LoginResult.isLoading -> {

                        showDialog = true
                        LoadingDialogBox(
                            showDialog = showDialog,
                            onDismiss = {
                                showDialog = false
                            }
                        )
                    }
                    is LoginResult.isSuccessful -> {
                        Toast.makeText(LocalContext.current ,"Login in successfully",Toast.LENGTH_SHORT).show()
                        showDialog = false
                        navController.navigate(Screen.NotesScreen.route)
                    }
                    is LoginResult.onFailure -> {
                        val errorMessage = (loginState as LoginResult.onFailure)
                        Toast.makeText(LocalContext.current ,errorMessage.message,Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }
                    else -> {}
                }
                ClickableText(
                    text = buildAnnotatedString {
                            append("Don`t have account ? ")
                            withStyle(style =  SpanStyle(
                                color = Primary
                            )
                            ){
                                append(" Sign up")
                            }
                        },

                    onClick = {
                        navController.navigate(Screen.SignUpScreen.route)
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
    }
}
