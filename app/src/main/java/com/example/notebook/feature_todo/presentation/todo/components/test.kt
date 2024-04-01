package com.example.notebook.feature_todo.presentation.todo.components



import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.notebook.R
import com.example.notebook.feature_todo.domain.model.ChecklistItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestCheckItems(
    item : ChecklistItem,
    onStatusChange:(Boolean)->Unit,
    onValueChange:(String)->Unit
) {

    var isChecked by remember { mutableStateOf(item.isCompleted) }
    var title by remember {
        mutableStateOf(item.title)
    }

    var isEnable  by remember {
        mutableStateOf(false)
    }

    val newTitle = remember {
        mutableStateOf("")
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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked  = it
                    })
                TextField(
                    enabled =  isEnable,
                    value = title,
                    onValueChange ={
                        title = it
                },

                )
            }

            IconButton(
                onClick = {
                    isEnable = !isEnable
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

        if(isChecked){
            Divider(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}