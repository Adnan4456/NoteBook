package com.example.notebook.feature_verify_user.presentation.ui


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.example.notebook.components.*
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_verify_user.presentation.VerifyUserEvent


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: VerificationViewModel = hiltViewModel()
){


    var showDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    val verificationState by viewModel.verificationState.collectAsState()

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

                IconButton(
                    onClick = {

                    }) {
                }

                Spacer(modifier = Modifier.height(50.dp))
                Text("Please again verify to see personal notes")

                Spacer(modifier = Modifier.height(20.dp))

                viewModel.email?.let { email ->
                    OutlinedTextField(
                            value = email,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                Spacer(modifier = Modifier.height(8.dp))
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_pass),
                    painterResource(id = R.drawable.ic_lock),
                    onTextChanged = {passsword ->
                        viewModel.onEvent(VerifyUserEvent.PasswordChangeEvent(passsword))
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
                    "Verify" ,
                    isEnabled = true,
                    onClick = {
                        viewModel.onEvent(VerifyUserEvent.onSubmit)
                    })

                when(verificationState){

                    is LoginResult.isLoading -> {

                        showDialog = true
                        LoadingDialogBox(
                            showDialog = showDialog,
                            onDismiss = {
                                showDialog = false
                            }
                        )
                    }
                    is  LoginResult.isSuccessful ->{

                        Toast.makeText(LocalContext.current ,"Login in successfully", Toast.LENGTH_SHORT).show()
                        showDialog = false
                        navController.navigate(Screen.SecretNotes.route)
                    }
                    is  LoginResult.onFailure ->{
                        Toast.makeText(LocalContext.current ,"Wrong password . Please try again",Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }
                    else -> {}
                }
            }
        }
    }
}
