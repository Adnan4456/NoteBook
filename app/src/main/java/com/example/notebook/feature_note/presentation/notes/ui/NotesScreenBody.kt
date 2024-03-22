package com.example.notebook.feature_note.presentation.notes.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.bookmarked_notes.BookMarkedScreen
import com.example.notebook.feature_note.presentation.notes.NotesEvent
import com.example.notebook.feature_note.presentation.notes.NotesViewModel
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.feature_secret_note.presentation.ui.SecretNotes
import com.example.notebook.feature_verify_user.presentation.ui.VerificationScreen
import com.example.notebook.ui.theme.AppTheme
import com.example.notebook.ui.theme.Orientation
import kotlinx.coroutines.launch

@Composable
fun AllNotesList(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    if (AppTheme.orientation == Orientation.Portrait){

        if(state.notes.isEmpty()){
            NoNotesImage(navController)

        }else {

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(state.notes){ note ->

                    AnimatedVisibility(
                        visible = true ,
                        enter = fadeIn(animationSpec = tween(5000)),
                        exit = fadeOut(animationSpec =  tween(5000))
                    ) {

                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        BottomBarScreen.AddEditNoteScreen.route +
                                                "?noteId=${note.id}&noteColor=${note.color}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(NotesEvent.DeleteNote(note))

                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        context.getString(R.string.note_delete),
                                        actionLabel = context.getString(R.string.undo)
                                    )
                                    if (result == SnackbarResult.ActionPerformed){
                                        viewModel.onEvent(NotesEvent.RestoreNote)
                                    }
                                }
                            },
                            onBookMarkChange = {
                                viewModel.onEvent(NotesEvent.Bookmark(note))
                            },
                            onSecretClick = {
                                viewModel.onEvent(NotesEvent.MakeSecret(note))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

        }
    }
    else
    {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){


            items(state.notes){ note ->

                AnimatedVisibility(
                    visible = true ,
                    enter = fadeIn(animationSpec = tween(5000)),
                    exit = fadeOut(animationSpec =  tween(5000))
                ){

                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    BottomBarScreen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))

                            scope.launch {

                                val result = snackbarHostState.showSnackbar(
                                    context.getString(R.string.note_delete),
                                    actionLabel = context.getString(R.string.undo)
                                )
                                if (result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        },
                        onBookMarkChange = {
                            Log.d("TAG", "Bookmark in screen")
                            viewModel.onEvent(NotesEvent.Bookmark(note))
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Note is Bookmarked",
                                )
                            }
                        },
                        onSecretClick = {
                            viewModel.onEvent(NotesEvent.MakeSecret(note))
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }

    }

}

@Composable
fun FavouritesList(
    navController: NavController
) {
    BookMarkedScreen(navController)
}

@Composable
fun HiddenNotesList(
    navController: NavController,
) {
    var verified by remember {
        mutableStateOf(false)
    }
    if(verified){
        SecretNotes(navController)
    }else{
        VerificationScreen(navController = navController,
            onCompleteListener = {
                verified = true
            }
        )
    }
}

@Composable
fun TrashList() {
    Text(text = "Coming Soon")
}
