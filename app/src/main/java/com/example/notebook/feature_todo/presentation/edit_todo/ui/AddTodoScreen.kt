package com.example.notebook.feature_todo.presentation.edit_todo.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.example.notebook.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import java.lang.reflect.Modifier


@Composable
fun AddTodoScreen(){

    var title by remember {
        mutableStateOf("")
    }

    TransparentHintTextField(
        text = "Title",
        hint = "Hint",
        isHintVisible = true,
        onValueChanged = {

        },
        onFocusChange = {},
    )
}