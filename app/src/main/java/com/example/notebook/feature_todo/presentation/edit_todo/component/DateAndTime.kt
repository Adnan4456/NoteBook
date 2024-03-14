package com.example.notebook.feature_todo.presentation.edit_todo.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notebook.R


@Composable
fun DateAndTime() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    //call viewModel here
                }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp))
                }

                Text(text = "Select Date")
            }

            Row(modifier = Modifier
            .fillMaxWidth()
                .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    //call viewModel here
                }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp))
                }

                Text(text = "Select time")
            }
        }

    }
}