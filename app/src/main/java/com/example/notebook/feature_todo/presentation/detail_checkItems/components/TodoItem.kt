package com.example.notebook.feature_todo.presentation.detail_checkItems.components


import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.presentation.detail_checkItems.ui.TodoDetailScreenViewModel
import com.example.notebook.feature_todo.presentation.todo.TodoEvents
import com.example.notebook.feature_todo.presentation.todo.components.ShowCheckListItem
import com.example.notebook.feature_todo.presentation.todo.ui.TodoViewModel

@Composable
fun TodoItem(
    mytask: Todo,
    viewModel: TodoDetailScreenViewModel = hiltViewModel()
) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.all_notes_item)
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            focusedElevation = 10.dp,
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )
                .padding(8.dp),
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = viewModel.title.value,
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
                    Icon(
                        imageVector =Icons.Default.Delete ,
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
                    )
                    .padding(8.dp)
            ){
                Text(
                    text = viewModel.description.value,
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
            Spacer(modifier = Modifier
                .height(8.dp)
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                )
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    )
            ) {
                items(viewModel._checkList){ check->
                    CheckItems(check)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun previewTodoItem(){
//    TodoItem()
}