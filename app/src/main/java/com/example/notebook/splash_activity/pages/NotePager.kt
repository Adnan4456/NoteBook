package com.example.notebook.splash_activity.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.R
import com.example.notebook.splash_activity.component.ImageView

@Composable
fun NotePager() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .background(Color.White),
    ){

        Column(
                horizontalAlignment = Alignment.End
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            ImageView(
                resource = R.drawable.page_one,
                contentDescription ="NotesPager",
                modifier = Modifier
                    .padding(24.dp)
                    .weight(.8f)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.8f),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.box_color)
                ),
                elevation =  CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            ){

                Column(
                    modifier = Modifier.padding(24.dp)

                ) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Take Notes",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ))

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Quickly capture whats in your mind",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewNotePager(){

    NotePager()
}