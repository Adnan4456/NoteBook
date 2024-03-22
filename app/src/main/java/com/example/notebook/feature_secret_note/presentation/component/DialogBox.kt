package com.example.notebook.feature_secret_note.presentation.component

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.components.EmailTextFieldComponent
import com.example.notebook.components.LoadingDialogBox
import com.example.notebook.components.PasswordTextFieldComponent
import com.example.notebook.feature_login.domain.model.LoginResult
import com.example.notebook.feature_login.presentation.ui.LoginFormEvent
import com.example.notebook.feature_login.presentation.ui.LoginViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordDialog(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {

    val state by viewModel.loginState.collectAsState()

    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var showVerifyDialog by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    when (state) {
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
            Toast.makeText(LocalContext.current, "Verified successfully", Toast.LENGTH_SHORT).show()
            showDialog = false
            showVerifyDialog = false
            onSuccess.invoke()
        }
        is LoginResult.onFailure -> {
            val errorMessage = (state as LoginResult.onFailure)
            Toast.makeText(LocalContext.current, errorMessage.message, Toast.LENGTH_SHORT).show()
            showVerifyDialog = false
            showDialog = false
        }
        else -> {
            // Handle other states if needed
        }
    }

    if (showVerifyDialog) {
        AlertDialog(
            onDismissRequest = {
                showVerifyDialog = false
            },
            title = {
                Text("Enter Password")
            },
            text = {
                InputFields(viewModel)
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = true
                        showVerifyDialog = false
                        viewModel.onEvent(LoginFormEvent.Submit)
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showVerifyDialog = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    } else {
        Text("Password is correct!")
    }
}

@Composable
fun InputFields(viewModel: LoginViewModel){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        EmailTextFieldComponent(
            labelValue = stringResource(id = R.string.label_email),
            painterResource(id = R.drawable.message),
            onTextChanged = { email ->
                viewModel.onEvent(LoginFormEvent.EmailChanged(email))
            },
            errorStatus = !viewModel.state.emailError
        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordTextFieldComponent(
            labelValue = stringResource(id = R.string.label_pass),
            painterResource(id = R.drawable.ic_lock),
            onTextChanged = { password ->
                viewModel.onEvent(LoginFormEvent.EmailChanged(password))
            },
        )
    }
}
