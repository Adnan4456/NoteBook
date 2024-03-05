package com.example.notebook.feature_note.presentation.add_edit_note.ui


import android.annotation.SuppressLint

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.notebook.feature_note.presentation.add_edit_note.components.TransparentContentTextField
import com.example.notebook.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()

) {

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                is AddEditNoteViewModel.UiEvent.ShowSnackBar ->{
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote ->{
                    navController.navigateUp()
                }
            }
        }
    }

    val noteBackgroundAnimatable = remember {
        Animatable(
           Color(
               if(noteColor != -1) noteColor
               else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()
    
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach {color ->

                    val colorInt = color.toArgb()
                    
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                        )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,

            ) {
                TransparentHintTextField(
                    text =titleState.text ,
                    hint = titleState.hint,
                    onValueChanged = {
                        viewModel.onEvent(AddEditNoteEvent.EnterTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 25.sp
                    ),
                    modifier = Modifier.weight(1.3f)
                )

                Row(
                    modifier = Modifier
                        .weight(.7f)
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ElevatedButton(
                        onClick = {
//                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "")
                    }

                    ElevatedButton(
                        onClick = {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "")
                    }
//                    FilledIconButton(
////                        modifier = Modifier.fillMaxWidth(),
//
//                        onClick = {
//                            viewModel.onEvent(AddEditNoteEvent.SaveNote)
//                        }) {
//                        Icon(Icons.Default.Save, contentDescription = "")
//                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentContentTextField(
                modifier = Modifier.background(
                    color = noteBackgroundAnimatable.value
                ),
                text =contentState.text ,
                hint = contentState.hint,
                onValueChanged = {
                    viewModel.onEvent(AddEditNoteEvent.EnterContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = false,
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
            )
        }
    }
}
