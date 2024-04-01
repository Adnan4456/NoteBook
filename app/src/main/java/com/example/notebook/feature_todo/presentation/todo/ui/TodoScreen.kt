package com.example.notebook.feature_todo.presentation.todo.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.util.BottomBarScreen
import com.example.notebook.feature_todo.presentation.todo.components.TodoItem
import java.util.concurrent.TimeUnit


@Composable
fun TodoScreen(
    navController: NavHostController,
    viewModel: TodoViewModel = hiltViewModel()
){

    val state = viewModel.todoState.value
    val mylist = viewModel._stateList.collectAsState()

    Column() {
        Text(text = "Todo ")
        if(state.todo.isEmpty()){

            NoNotesImage()
        }
        else
        {
            LazyColumn(
                modifier = Modifier.background(
                    color = colorResource(id = R.color.all_notes_item)
                    )
            ){
                items(
                    state.todo
                ){task ->
                    TodoItem(
                        modifier = Modifier.clickable {
                        },
                        mytask = task,
                    navController)
                }
            }
        }
    }
}

@Composable
fun NoNotesImage(
){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

    val repeatableSpec = rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000),
                repeatMode = RepeatMode.Restart
            )
        )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        LottieAnimation(
            modifier = Modifier.size(250.dp),
            composition = composition,
            progress = repeatableSpec.value
        )
        Text(text = "Add todo!")
    }

}