package com.example.notebook.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.R
@Composable
fun LoginButton(
    value: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true
){

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
          colorResource(id = R.color.main_color),
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick.invoke()
        },
        enabled = isEnabled
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
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
