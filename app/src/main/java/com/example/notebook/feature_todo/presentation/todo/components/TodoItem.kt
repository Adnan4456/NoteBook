package com.example.notebook.feature_todo.presentation.todo.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.R
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.presentation.todo.TodoEvents
import com.example.notebook.feature_todo.presentation.todo.ui.TodoViewModel

@Composable
fun TodoItem(
    modifier: Modifier,
   mytask: Todo,
   viewModel: TodoViewModel = hiltViewModel()
) {

    Card (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.all_notes_item)
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            focusedElevation = 10.dp,
            defaultElevation = 4.dp
        )


    ){

        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                ).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = mytask.title,
            style = TextStyle(
                color =  colorResource(id = R.color.app_black),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ))

            Row(
                modifier = Modifier
                    .background(
                color = colorResource(id = R.color.all_notes_item)
            ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector =Icons.Default.Check ,
                    contentDescription = "",
                    tint = Color.Green
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector =Icons.Default.Edit ,
                    contentDescription = "",
                    tint = Color.Blue
                )

                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector =Icons.Default.Delete ,
                    contentDescription = "",
                    tint = Color.Red
                )
            }
        }
        Divider(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )
        )
        Column (
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )  .padding(8.dp)
        ){
            Text(text = mytask.description,
                style = TextStyle(
                    color =  colorResource(id = R.color.app_black),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = mytask.date.toString())
                Text(text = mytask.timestamp.toString())
            }
        }
        Spacer(modifier = Modifier.height(8.dp)
            .background(
                color = colorResource(id = R.color.all_notes_item)
            ))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )
        ) {
            if (mytask.checklist.size>0){
                CheckItems(mytask.checklist.get(0),
                onStatusChange = { newValue ->
                    Log.d("CheckItems", "New value of TextField: $newValue")
                    viewModel.update(mytask)
                },
                onValueChange = {
                 Log.d("Title","new title${it}")
                })
            }
            Spacer(modifier = Modifier.height(2.dp))
            if (mytask.checklist.size>1){

                CheckItems(mytask.checklist.get(1),
                onStatusChange = {newValue ->
                    Log.d("CheckItems", "New value of TextField: $newValue")
//                    viewModel.onEvent(TodoEvents.EditCheckItem(mytask , mytask.checklist.get(1)))
                                 },
                    onValueChange = {
                        Log.d("Title","new title${it}")
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun previewTodoItem(){
//    TodoItem()
}