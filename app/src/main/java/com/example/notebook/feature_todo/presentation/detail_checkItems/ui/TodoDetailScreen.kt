package com.example.notebook.feature_todo.presentation.todo.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.feature_todo.presentation.detail_checkItems.components.DialogBox
import com.example.notebook.feature_todo.presentation.detail_checkItems.components.TodoItem
import com.example.notebook.feature_todo.presentation.detail_checkItems.ui.TodoDetailScreenViewModel



@Composable
fun TodoDetailScreen(
    viewModel: TodoDetailScreenViewModel = hiltViewModel(),
) {

    val state = viewModel.todoState.value

    if(state.todo == null) {
        Log.d("TAG","null todo")
    }
    else
    {
        TodoItem(
            mytask = state.todo,
        )

//        if(viewModel.taskCompleted.value){
//            DialogBox(showDialog = viewModel.taskCompleted.value) {
////               showDialogbox = false
//            }
//        }
    }
}