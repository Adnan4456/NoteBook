package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.notebook.feature_todo.presentation.components.CheckableItem
import com.example.notebook.feature_todo.presentation.components.TimePickerDialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddTodoScreen(
//    navController: NavHostController,
//    edit: Boolean?,
//    taskId: Long?,
    viewModel: AddTodoViewModel = hiltViewModel()
) {

    val dateTime = LocalDateTime.now()

    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute
        )
    }
    val datePickerState = rememberDatePickerState()

    val color = Color(0xDEDEDEDE)

    val systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.all_notes_item)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Todo",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ))

            TextButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(37.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
//                    Log.d("richtext" , "${viewModel.editorTitleState.toString()}")
//                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                    viewModel.onSave()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_black)
                )
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        color = colorResource(id = R.color.box_color)
                    ))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = colorResource(id = R.color.all_notes_item)
                ),
            horizontalAlignment = Alignment.Start,
        ) {

//            item {
//            Spacer(modifier = Modifier.height(30.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                Text(text = "Todo",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 28.sp
//                    ))
//
//                TextButton(
//                    modifier = Modifier
//                        .width(80.dp)
//                        .height(37.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    onClick = {
////                    Log.d("richtext" , "${viewModel.editorTitleState.toString()}")
////                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = colorResource(id = R.color.app_black)
//                    )
//                ) {
//                    Text(
//                        text = "Save",
//                        style = TextStyle(
//                            color = colorResource(id = R.color.box_color)
//                        ))
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//            Divider()
//            Spacer(modifier = Modifier.height(20.dp))
//            }

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
            item{

                Text(text = "Select Date")

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
                        .clickable {
                            viewModel.showDatePickerDialog.value = true
                        }
                        .padding(8.dp),

                    value = viewModel.date.value,
                    onValueChange = {
                        viewModel.date.value = it
                    },
                    singleLine = true,
                    maxLines = 1,
                    enabled = false,

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Spacer(modifier = Modifier.height(20.dp))
                if(viewModel.showDatePickerDialog.value){

                    DatePickerDialog(
                        onDismissRequest = {
                            viewModel.showDatePickerDialog.value = false
                        },

                        confirmButton = {
                            TextButton(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =  colorResource(id = R.color.main_color)
                                ),
                                onClick = {

                                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    val selectedDateString = datePickerState.selectedDateMillis?.let {
                                        Date(
                                            it
                                        )
                                    }?.let { dateFormat.format(it) }
                                    Log.d("Date","${selectedDateString}")
                                    if (selectedDateString != null) {
                                        viewModel.date.value = selectedDateString
                                    }else
                                    {
                                        viewModel.date.value = "Please select a date"
                                    }
                                    viewModel.showDatePickerDialog.value = false

                                }) {
                                Text(text = "Confirm",
                                    style = TextStyle(
                                        fontSize = 16.sp,

                                        )
                                )
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState
                        )
                    }
                }
            }

            item{

                Text(text = "Select Time")

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
                        .clickable {
                            viewModel.showTimePickerDialog.value = true
                        }
                        .padding(8.dp),

                    value = viewModel.time.value,
                    onValueChange = {
                        if(viewModel.time.value == null){
                            viewModel.time.value = "Please select time "
                        }
                        else
                        {
                            viewModel.time.value = it
                        }
                    },
                    singleLine = true,
                    maxLines = 1,
                    enabled = false,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Spacer(modifier = Modifier.height(20.dp))

                if(viewModel.showTimePickerDialog.value){

                    TimePickerDialog(
                        onCancel = {
                            viewModel.showTimePickerDialog.value = false
                        },
                        onConfirm = {
                            viewModel.time.value = "${timePickerState.hour}" + ":${timePickerState.minute}"
                            viewModel.showTimePickerDialog.value = false
                        }
                    ){
                        TimePicker(
                            state = timePickerState
                        )
                    }
                }

            }

            item{

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Do you want to set Alarm")
                    Switch(
                        checked = viewModel.alarmSet.value ,
                        onCheckedChange ={
                            viewModel.alarmSet.value = it
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "",
                        color = colorResource(id = R.color.app_black)
                    )
                }
            }
            if(viewModel.checkList.size == 0){
                item {


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Button(
                            onClick = {
                                viewModel.onAddCheckable()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.app_black),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Add Todo",
                                style = TextStyle(
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }

                }
            }
            else {

                items(
                    viewModel.checkList,
                    key = {
                        it.uid
                    }
                ){
                    //here choe layout of
                    CheckableItem(it)
                }
                item{
                    Spacer(
                        modifier = Modifier.height(60.dp)
                    )
                }
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


