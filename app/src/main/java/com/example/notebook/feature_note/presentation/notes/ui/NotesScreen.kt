package com.example.notebook.feature_note.presentation.notes.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.notes.NotesEvent
import com.example.notebook.feature_note.presentation.notes.NotesViewModel
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.notes.components.OrderSection
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.ui.theme.AppTheme
import com.example.notebook.ui.theme.Orientation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    firbaseAuth: FirebaseAuth
) {

    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val density = LocalDensity.current

    val searchQuery = remember { viewModel.searchQuery }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row( verticalAlignment = Alignment.CenterVertically) {


                    IconButton(onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort Note"
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.notetitle),
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                        firbaseAuth.signOut()
                            navController.navigate(Screen.LoginScreen.route)
                    },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Log Out"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp)
            ){

                Row (
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ){
                    BasicTextField(
                        modifier = Modifier.weight(1f),
                        value = searchQuery.value,
                        singleLine = true,
                        onValueChange = {
                            viewModel.onSearchQueryChanged(it)
                        })
                    IconButton(
                        onClick = {
                            viewModel.onSearchQueryChanged("")
                        }
                    ) {
                        Icon(
                            imageVector = if(searchQuery.value.isEmpty()){
                                Icons.Default.Search
                            }else
                            {
                                Icons.Default.Cancel
                            },
                            contentDescription = "Search Note"
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = slideInVertically {

                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (AppTheme.orientation == Orientation.Portrait){

                if(state.notes.isEmpty()){
                    NoNotesImage()
                }else {

                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
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
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
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
                                viewModel.onEvent(NotesEvent.DeleteNote(note))

                                scope.launch {
//                                    val result = scaffoldState.snackbarHostState.showSnackbar(
//                                        context.getString(R.string.note_delete),
//                                        actionLabel = context.getString(R.string.undo)
//                                    )
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
fun NoNotesImage(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

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
        Text(text = "Create your first note !")
    }

}