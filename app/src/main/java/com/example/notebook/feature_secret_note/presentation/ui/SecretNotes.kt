package com.example.notebook.feature_secret_note.presentation.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.components.LoadingDialogBox
import com.example.notebook.components.LoginButton
import com.example.notebook.components.PasswordTextFieldComponent
import com.example.notebook.feature_login.presentation.ui.LoginFormEvent
import com.example.notebook.feature_note.presentation.bookmarked_notes.EmptyBookMark
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkEvent
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_secret_note.presentation.SecretNotesEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import javax.inject.Inject


@Composable
fun SecretNotes(
    navController : NavController,
    viewModel: SecretNoteViewModel = hiltViewModel()
) {
//    val auth = FirebaseAuth.getInstance()
//    val currentUser = auth.currentUser

    val context = LocalContext.current

    val scope = rememberCoroutineScope()
//    Text(text = "Secret notes screen")

    val state by viewModel.notesList.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }

    when{
        state.isLoading -> {

            Column() {
                showDialog = true
                LoadingDialogBox(
                    showDialog = showDialog,
                    onDismiss = {
                        showDialog = false
                    }
                )
            }
        }
        state.notes.isEmpty() ->{
            showDialog = false
            EmptySecretNotes()
        }
        state.notes.isNotEmpty() -> {

            showDialog = false
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 30.dp)
                    .padding(8.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(state.notes){ note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier

                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(SecretNotesEvent.onDelete(note))

                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    context.getString(R.string.note_delete),
                                    actionLabel = context.getString(R.string.undo)
                                )
                                if (result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(SecretNotesEvent.RestoreNote)
                                }
                            }
                        },
                        onBookMarkChange = {
                            viewModel.onEvent(SecretNotesEvent.onBookMark(note))
                        },
                        onSecretClick = {
                            viewModel.onEvent(SecretNotesEvent.MakeUnSecret(note))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }
}

@Composable
fun EmptySecretNotes(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.emptybookmark))

    val repeatableSpec = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = repeatableSpec.value
        )
        Text(text = "You don`t have any secret Note.")
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
