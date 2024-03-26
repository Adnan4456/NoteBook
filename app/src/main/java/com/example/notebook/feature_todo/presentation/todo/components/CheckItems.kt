package com.example.notebook.feature_todo.presentation.todo.components

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.feature_todo.domain.model.ChecklistItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckItems(
    item : ChecklistItem,
    onStatusChange:(Boolean)->Unit,
    onValueChange:(String)->Unit
) {

    val isChecked = remember { mutableStateOf(item.isCompleted) }

    val isEnable  = remember {
        mutableStateOf(false)
    }

    val newTitle = remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
//                vm.onCheckableDelete(item)
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Checkable",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = colorResource(id = R.color.selected).copy(
                            alpha = .5f
                        )
                    ),
                elevation = CardDefaults.cardElevation(
                    focusedElevation = 10.dp,
                    defaultElevation = 8.dp
                )
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Checkbox(
                        checked = isChecked.value ,
                        onCheckedChange = {
                            item.isCompleted  = it
                            isChecked.value = it
                            onStatusChange(it)
                        },
                    )
                    TextField(
                        enabled = isEnable.value,
                        value = item.title,
                        onValueChange = {
//                        textFieldValue = it.copy(text = it.text.trim())
//                        vm.onCheckableValueChange(item,textFieldValue.text)
                            item.title = it
                            newTitle.value = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = "",
                                fontStyle = FontStyle.Italic
                            )
                        },
                        modifier = Modifier
                            .padding(start = 0.dp)
                            .onKeyEvent {
                                Log.d("flfklskf", it.nativeKeyEvent.keyCode.toString())
                                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
//                                vm.onAddCheckable(item)
                                    true
                                } else if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL) {
//                                vm.onBackOnValue(
//                                    item,
//                                    currentPos,
//                                    textFieldValue.selection.start
//                                )
//                                currentPos = textFieldValue.selection.start
                                    true
                                } else {
                                    false
                                }

                            },
//                        .focusRequester(focusRequester)
//                        .onFocusChanged {
//                            if (it.isFocused) {
//                                vm.onFocusGot(item)
//                            }
//                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )
                }
            }

            IconButton(
                onClick = {
                    isEnable.value = !isEnable.value
                },
                modifier = Modifier.size(32.dp)
            ) {
                if(isEnable.value){

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Delete Checkable",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    onValueChange.invoke(newTitle.value)

                }else{
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Delete Checkable",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }

        }

        if(isChecked.value){
            Divider(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}