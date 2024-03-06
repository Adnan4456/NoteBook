package com.example.notebook.feature_secret_note.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.components.LoginButton
import com.example.notebook.components.PasswordTextFieldComponent
import com.example.notebook.feature_login.presentation.ui.LoginFormEvent
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


@Composable
fun SecretNotes(
    navController : NavController,
    viewModel: SecretNoteViewModel = hiltViewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userEmail = currentUser?.email

    val context = LocalContext.current


    PasswordDialog()

    if (userEmail!= null ){

    }else
    {
        Toast.makeText(context,"Please login again",Toast.LENGTH_LONG).show()

    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordDialog() {
    var password by remember { mutableStateOf("") }
    var isPasswordCorrect by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    if (!isPasswordCorrect) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text("Enter Password")
            },
            text = {

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.label_pass),
                    painterResource(id = R.drawable.ic_lock),
                    onTextChanged = {passsword ->
//                        viewModel.onEvent(LoginFormEvent.PasswordChanged(passsword))
                    },
//                    errorStatus = viewModel.state.passwordError
                )

//                TextField(
//                    value = password,
//                    onValueChange = {
//                        password = it
//                    },
//                    label = {
//                        Text("Password")
//                    },
//                    keyboardOptions = KeyboardOptions.Default.copy(
//                        keyboardType = KeyboardType.Password
//                    ),
//                    isError = password.isNotEmpty() && !isPasswordCorrect,
//                    colors = TextFieldDefaults.textFieldColors(
//                        textColor = MaterialTheme.colorScheme.onSurface,
////                        color = Color.Transparent,
//                        cursorColor = MaterialTheme.colorScheme.primary
//                    ),
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                )
            },
            confirmButton  = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (password == "your_password") {
                                isPasswordCorrect = true
                            } else {

                                focusManager.clearFocus()
                                keyboardController?.hide()
                                password = ""
                            }
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        )
    } else {
        Text("Password is correct!")
    }

}
