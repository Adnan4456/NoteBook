package com.example.notebook.feature_signup.presentation.ui

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.example.notebook.common.rememberImeState
import com.example.notebook.components.*
import com.example.notebook.feature_internet_connectivity.domain.ConnectivityObserver
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_signup.domain.model.SignUpResult
import kotlinx.coroutines.launch

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

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(600))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,

    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(scrollState)
                .padding(8.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
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
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription ="",
                        tint = colorResource(id = R.color.main_color)
                        )
                    }
                    NormalTextComponent(
                        value = stringResource(id = R.string.create_account),
                    )
                }

                Column(

                ) {
                    HeadingTextComponent(
                        value = stringResource(id = R.string.create_account),
                    )
                    NormalTextComponent(
                        value = stringResource(id = R.string.Hi),
                    )

                }

                Column(

                ){


                    EmailTextFieldComponent(
                        labelValue = stringResource(id = R.string.user_name),
                        painterResource(id = R.drawable.ic_person),
                        onTextChanged = {email ->
                            viewModel.onEvent(RegistrationFormEvent.EmailChanged(email))
                        },
                        errorStatus = !viewModel.state.emailError
                    )

                    Spacer(modifier = Modifier.height(8.dp))

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

                    Spacer(modifier = Modifier.height(8.dp))

                    PasswordTextFieldComponent(
                        labelValue = stringResource(id = R.string.label_pass_confirm),
                        painterResource(id = R.drawable.ic_lock),
                        onTextChanged = {passsword ->
                            viewModel.onEvent(RegistrationFormEvent.PasswordChanged(passsword))
                        },
                        errorStatus = viewModel.state.passwordError
                    )
                }

                LoginButton(
                    stringResource(id = R.string.create_account) ,
                    isEnabled = true,
                    onClick = {
                        //first check internet connection
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
                        val errorMessage = (signupState as SignUpResult.onFailure)
                        Toast.makeText(LocalContext.current ,errorMessage.message, Toast.LENGTH_SHORT).show()
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
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.LoginScreen.route)
                            },
                            text =  "Already have account ?",
                        )
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.LoginScreen.route)
                            },
                            text = buildAnnotatedString {
                                withStyle(style =  SpanStyle(
                                    color = colorResource(id = R.color.main_color)
                                )
                                ){
                                    append(" Login here ")
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
//        SignUpScreen()
    }
}