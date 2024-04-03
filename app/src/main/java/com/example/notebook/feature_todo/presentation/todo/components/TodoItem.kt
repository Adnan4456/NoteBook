package com.example.notebook.feature_todo.presentation.todo.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.presentation.todo.ui.TodoViewModel

@Composable
fun TodoItem(
    modifier: Modifier,
    mytask: Todo,
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {

    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            focusedElevation = 10.dp,
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )
                .padding(16.dp),
        ) {

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = mytask.title,
                    style = TextStyle(
                        color =  colorResource(id = R.color.app_black),
                        fontSize = 20.sp
                    ))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                        Icon(
                            imageVector =Icons.Default.Check ,
                            contentDescription = "",
                            tint = Color.Green
                        )

                        Icon(
                            imageVector =Icons.Default.Edit ,
                            contentDescription = "",
                            tint = Color.Blue
                        )

                        Icon(
                            modifier = Modifier.clickable {
                                viewModel.onTaskDelete(task = mytask)
                            },
                            imageVector =Icons.Default.Delete ,
                            contentDescription = "",
                            tint = Color.Red
                        )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Column (
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    )
            ){
                Text(text = mytask.description,
                    style = TextStyle(
                        color =  colorResource(id = R.color.app_black),
                        fontSize = 16.sp,
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = mytask.date.toString())
                    Text(text = viewModel.convertLongToTime(mytask.timestamp!!))
                }
            }
            Spacer(modifier = Modifier
                .height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                if (mytask.checklist.size>0){
                    ShowCheckListItem(mytask.checklist.get(0))
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (mytask.checklist.size>1){
                    ShowCheckListItem(mytask.checklist.get(1))
                }

                if (mytask.checklist.size>2){
                    Text(
                       modifier = Modifier.padding(start = 16.dp),
                        text = "...",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            BottomBarScreen.TodoDetailScreen.route +
                                    "todoId=${mytask.id}",
                        )
                    },
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                BottomBarScreen.TodoDetailScreen.route +
                                        "todoId=${mytask.id}",
                            )
                        },
                    text = " See All ",
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
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