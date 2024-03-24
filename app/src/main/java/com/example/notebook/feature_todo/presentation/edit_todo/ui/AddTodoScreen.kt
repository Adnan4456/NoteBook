package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.R
import com.example.notebook.feature_todo.presentation.edit_todo.component.DateAndTime
import com.google.accompanist.systemuicontroller.rememberSystemUiController


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

    val color = Color(0xDEDEDEDE)

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = colorResource(id = R.color.all_notes_item)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Todo",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ))
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Text(text = "Title")
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.selected).copy(
                            alpha = .5f
                        )
                    )

                    .padding(8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),

                value = viewModel.title.value,
                onValueChange = {
                    viewModel.setTitle(it)
                },
                singleLine = true,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {

            Text(text = "Description")

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = colorResource(id = R.color.selected).copy(
                            alpha = .5f
                        )
                    )

                    .padding(8.dp),
                value = viewModel.description.value,
                onValueChange = {
                    viewModel.setDescription(it)
                },
                singleLine = true,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
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


