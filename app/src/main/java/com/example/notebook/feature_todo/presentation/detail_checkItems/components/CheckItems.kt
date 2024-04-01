package com.example.notebook.feature_todo.presentation.detail_checkItems.components

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.R
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.presentation.detail_checkItems.ui.TodoDetailScreenViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CheckItems(
    item : ChecklistItem,
    viewModel: TodoDetailScreenViewModel = hiltViewModel()
) {

    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(item.title))
    }
    val (focusRequester) = FocusRequester.createRefs()


    var isChecked by remember { mutableStateOf(item.isCompleted) }

    var isEnable  by remember {
        mutableStateOf(false)
    }


    var newTitle by remember {
        mutableStateOf(item.title)
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(
            color = colorResource(id = R.color.all_notes_item)
        ),
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
                          //Delete checkItem
                viewModel.onCheckableDelete(item)
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Checkable",
                    tint = Color.Red,
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    focusedElevation = 10.dp,
                    defaultElevation = 8.dp
                )
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            Log.d("TAG","${item.isCompleted}")
                            viewModel.onCheckableCheck(item,it)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.main_color),
                            uncheckedColor = colorResource(id = R.color.black)
                        ),
                    )
                    TextField(
                        enabled = isEnable,
                        modifier= Modifier.fillMaxWidth(),
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it.copy(text = it.text.trim())
                            viewModel.onCheckableValueChange(item,textFieldValue.text)
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }
            IconButton(
                onClick = {
                    isEnable = !isEnable
//                    Log.d("TAG","enable = ${ isEnable}")
                },
                modifier = Modifier.size(32.dp)
            ) {
                if(isEnable){

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Delete Checkable",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
//                    onValueChange.invoke(newTitle.value)

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

        if(isChecked){
            Divider(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}