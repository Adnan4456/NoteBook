package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.feature_todo.presentation.edit_todo.component.DateAndTime


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTodoScreen(
//    navController: NavHostController,
//    edit: Boolean?,
//    taskId: Long?,
//    viewModel: AddTodoViewModel = hiltViewModel()
) {

   var viewModel: AddTodoViewModel = hiltViewModel()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item() {
            TextField(
                value = viewModel.title.value,
                onValueChange = {
                    viewModel.setTitle(it)
                },
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(text = "Title")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }


        item (){

            TextField(
                value = viewModel.description.value,
                onValueChange = {
                    viewModel.setDescription(it)
                },
                singleLine = true,
                maxLines = 1,
                label = {
                    Text(text = "Title")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item (){
            DateAndTime()
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){

            }
        }
    }
}

@Preview
@Composable
fun previewAddTodo(){

    AddTodoScreen(
//        viewModel: AddTodoViewModel = hiltViewModel()
    )
}


