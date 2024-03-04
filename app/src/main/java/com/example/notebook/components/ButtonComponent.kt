package com.example.notebook.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.ui.theme.Purple500
import com.example.notebook.ui.theme.Purple700


@Composable
fun LoginButton(
    value: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true
){

    Button(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick.invoke()
        },
        enabled = isEnabled
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Purple500, Purple700)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){

            Text(
                text = value ,
                fontSize = 18.sp ,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
