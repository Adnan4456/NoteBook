package com.example.notebook.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.notebook.R


@Composable
fun LoadingDialogBox(
    showDialog: Boolean,
    onDismiss: ()->Unit
){
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            DialogProperties(dismissOnBackPress = false,
                dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                         modifier = Modifier
                             .size(100.dp)
                             .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.main_color),
                    )
                }
            }
        }
}