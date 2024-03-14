package com.example.notebook.feature_todo.presentation.edit_todo.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.feature_todo.presentation.components.DatePickerScreen
import com.example.notebook.feature_todo.presentation.components.TimePickerScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndTime() {

    val datePickerState = rememberDatePickerState()
    var showDate by remember {
        mutableStateOf(false)
    }

    val timePickerState = rememberTimePickerState()
    var showTime by remember{
        mutableStateOf(false)
    }
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
                    showDate = true
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
                 showTime = true
                }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp))
                }

                Text(text = "Select time")
            }
        }

        if (showDate){
            DatePickerScreen()
        }
        if (showTime){
            TimePickerScreen()
        }
    }
}