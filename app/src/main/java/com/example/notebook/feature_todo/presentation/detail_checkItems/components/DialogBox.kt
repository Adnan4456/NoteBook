package com.example.notebook.feature_todo.presentation.detail_checkItems.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R

@Composable
fun DialogBox(
        showDialog: Boolean,
        onDismiss: ()->Unit
){
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment= Alignment.Center,
                    modifier = Modifier
                        .size(500.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                        AnimationLottie()
                }
            }
        }
}

@Composable
fun AnimationLottie() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.task_completed))

    val repeatableSpec = rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000),
//                repeatMode = RepeatMode.Restart
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
        Text(text = "Task is completed successfully")
    }
}