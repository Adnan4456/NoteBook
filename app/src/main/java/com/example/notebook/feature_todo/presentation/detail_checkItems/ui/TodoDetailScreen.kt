package com.example.notebook.feature_todo.presentation.todo.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.feature_todo.presentation.detail_checkItems.components.TodoItem
import com.example.notebook.feature_todo.presentation.detail_checkItems.ui.TodoDetailScreenViewModel



@Composable
fun TodoDetailScreen(
    navController: NavController,
    viewModel: TodoDetailScreenViewModel = hiltViewModel(),
) {

    var state = viewModel.todoState.value

    if(state.todo == null) {
        Log.d("TAG","null todo")
    }
    else
    {
        Log.d("TAG","Todo has value ${state.todo!!.title}")

        TodoItem(
            mytask = state.todo!!,
        )
    }
}