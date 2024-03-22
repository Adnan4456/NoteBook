package com.example.notebook.feature_note.presentation.bookmarked_notes


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.components.LoadingDialogBox
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkEvent

import com.example.notebook.feature_note.presentation.bookmarked_notes.ui.BookMarkedViewModel
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import kotlinx.coroutines.launch


@Composable
fun BookMarkedScreen(
    navController: NavController,
    viewModel: BookMarkedViewModel = hiltViewModel()
){

    val state by viewModel.noteList.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
            EmptyBookMark()
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
                                viewModel.onEvent(BookMarkEvent.onDelete(note))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        context.getString(R.string.note_delete),
                                        actionLabel = context.getString(R.string.undo)
                                    )
                                    if (result == SnackbarResult.ActionPerformed){
                                        viewModel.onEvent(BookMarkEvent.RestoreNote)
                                    }
                                }
                            },
                            onBookMarkChange = {
                                viewModel.onEvent(BookMarkEvent.onBookMark(note))
                            },
                            onSecretClick = {
                                viewModel.onEvent(BookMarkEvent.MakeSecret(note))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}

@Composable
fun EmptyBookMark(){

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
        Text(text = "Add notes to Bookmark !")
    }

}