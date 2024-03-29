package com.example.notebook.feature_login.presentation.ui

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.*
import com.example.notebook.common.rememberImeState
import com.example.notebook.components.*
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_note.presentation.util.AuthScreen
import com.example.notebook.navigation.Graph
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
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(600))
        }
    }

//    if (networkStatus == ConnectivityObserver.Status.Available) {
//        LaunchedEffect(networkStatus) {
//            snackbarHostState.showSnackbar(
//                message = when (networkStatus) {

//                    ConnectivityObserver.Status.Unavailable -> "No internet connection."
//                    ConnectivityObserver.Status.Available -> "Available network connection."
//                    ConnectivityObserver.Status.Losing -> "Losing network connection."
//                    ConnectivityObserver.Status.Lost -> "Lost network connection."
//                },
//                actionLabel = context.getString(R.string.dismiss)
//            )
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    IconButton(onClick = {

                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription ="",
                            tint = colorResource(id = R.color.main_color)
                        )
                    }
                    NormalTextComponent(
                        value = stringResource(id = R.string.logintext),
                    )
                }

                Column() {


                    HeadingTextComponent(
                        value = stringResource(id = R.string.welcome),
                    )

                    NormalTextComponent(
                        value = stringResource(id = R.string.loginText1),
                    )
                }

                Column {

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

                    Spacer(modifier = Modifier.height(16.dp))

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

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ){
                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.forget_password)),
                            onClick = {
                                //navigate to forget password screen
                                navController.navigate(AuthScreen.ForgetPasswordScreen.route)
                            }
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
                        showDialog = false

//                        navController.popBackStack()
                        navController.navigate(Graph.HOME){
                            popUpTo(AuthScreen.LoginScreen.route){
                                inclusive = true
                            }
                        }
                    }
                    is LoginResult.onFailure -> {
                        val errorMessage = (loginState as LoginResult.onFailure)
                        Toast.makeText(LocalContext.current ,errorMessage.message,Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }
                    else -> {}
                }


                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ){

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){


                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate(AuthScreen.LoginScreen.route)
                            },
                            text =  "Don`t have account yet  ?",
                        )
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate(AuthScreen.SignUpScreen.route)
                            },
                            text = buildAnnotatedString {
                                withStyle(style =  SpanStyle(
                                    color = colorResource(id = R.color.main_color)
                                )
                                ){
                                    append(" Create an account here ")
                                }
                            },
                        )

                    }
                }
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
