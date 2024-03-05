package com.example.notebook.feature_note.presentation.bookmarked_notes


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarResult

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.R

import com.example.notebook.feature_note.presentation.bookmarked_notes.ui.BookMarkedViewModel
import com.example.notebook.feature_note.presentation.notes.NotesEvent
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch


@Composable
fun BookMarkedScreen(
    viewModel: BookMarkedViewModel = hiltViewModel()
){

    val state by viewModel.noteList.collectAsState()

    when{
        state.isLoading -> {
            CircularProgressIndicator()
        }
        state.notes?.isNotEmpty() == true -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(state.notes!!){ note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier

                            .fillMaxWidth()
                            .clickable {
//                                navController.navigate(
//                                    Screen.AddEditNoteScreen.route +
//                                            "?noteId=${note.id}&noteColor=${note.color}"
//                                )
                            },
                        onDeleteClick = {
//                            viewModel.onEvent(NotesEvent.DeleteNote(note))

//                            scope.launch {
//                                val result = snackbarHostState.showSnackbar(
//                                    context.getString(R.string.note_delete),
//                                    actionLabel = context.getString(R.string.undo)
//                                )
//                                if (result == SnackbarResult.ActionPerformed){
//                                    viewModel.onEvent(NotesEvent.RestoreNote)
//                                }
//                            }
                        },
                        onBookMarkChange = {
//                            viewModel.onEvent()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}