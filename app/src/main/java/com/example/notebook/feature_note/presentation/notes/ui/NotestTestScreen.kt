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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import com.example.notebook.feature_note.presentation.notes.components.CardRow
import com.example.notebook.feature_note.presentation.notes.components.NoteItem
import com.example.notebook.feature_note.presentation.notes.components.SearchBar
import com.example.notebook.feature_note.presentation.notes.components.header
import com.example.notebook.feature_note.presentation.util.Screen
import com.example.notebook.feature_secret_note.presentation.ui.SecretNotes
import com.example.notebook.feature_verify_user.presentation.ui.VerificationScreen
import com.example.notebook.ui.theme.AppTheme
import com.example.notebook.ui.theme.Orientation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@Composable
fun NotesTestScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    firbaseAuth: FirebaseAuth
){

    Surface() {
        Column() {

            SearchBar({query ->
                println("Search query: $query")
            })

            Spacer(modifier = Modifier.height(16.dp))
            GridLayout(navController)
        }
    }
}

@Composable
fun GridLayout(navController: NavController) {
    val context = LocalContext.current
    val (selectedCardIndex, setSelectedCardIndex) = remember { mutableStateOf("") }



    Column(modifier = Modifier.fillMaxSize()) {

        CardRow(onClick = { index -> setSelectedCardIndex(index) })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)){

            when(selectedCardIndex){
                "AllNotes" -> AllNotesList(navController)
                "Hidden Notes" -> HiddenNotesList(navController)
                "Favourites"-> FavouritesList(navController)
                "Trash" -> TrashList()
            }
            Toast.makeText(context,"$selectedCardIndex", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun AllNotesList(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val density = LocalDensity.current

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
                header {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                            )
                            .padding(16.dp),
                        text = ("Notes"),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
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
                            },
                            onSecretClick = {
                                viewModel.onEvent(NotesEvent.MakeSecret(note))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                //second header for testing
                header {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray,
                            )
                            .padding(16.dp),
                        text = ("Notes"),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
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
            header {
                Text(text = ("Notes"))
            }

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
    ListContent(title = "Trash")
}

@Composable
fun ListContent(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        val itemList = List(20) { "List item ${it + 1}" }

        val rows = mutableListOf<List<String>>()
        for (i in itemList.indices step 3) {
            if (i + 2 < itemList.size) {
                rows.add(itemList.subList(i, i + 3))
            } else if (i + 1 < itemList.size) {
                rows.add(itemList.subList(i, i + 2))
            } else {
                rows.add(listOf(itemList[i]))
            }
        }

        rows.forEachIndexed { index, rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (index % 2 == 0) {
                    // Display two items in this row
                    Text(
                        text = rowItems.getOrNull(0) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = rowItems.getOrNull(1) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // Display one item in the middle of the row
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = rowItems.getOrNull(0) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

