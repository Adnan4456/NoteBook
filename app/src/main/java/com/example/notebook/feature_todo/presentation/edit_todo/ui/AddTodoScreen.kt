package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notebook.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import java.lang.reflect.Modifier


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTodoScreen(
    navController: NavHostController,
    edit: Boolean?,
    taskId: Long?,
   viewModel: AddTodoViewModel = hiltViewModel()
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Todo"
//                        text = if(vm.editing) stringResource(id = R.string.edit_task) else  stringResource(id = R.string.add_task),
//                        color = if(isSystemInDarkTheme()) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
//                            vm.onBackPressed()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
//                            vm.onTaskAdd()
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "Save")
                    }
                }
            )
        }
    ) {

    }
}


