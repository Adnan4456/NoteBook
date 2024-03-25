package com.example.notebook.feature_todo.presentation.components




import android.util.Log
import android.view.KeyEvent.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notebook.feature_todo.presentation.edit_todo.ui.AddTodoViewModel
import com.example.notebook.R
import com.example.notebook.feature_todo.domain.model.ChecklistItem

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckableItem(
    item: ChecklistItem,
    vm: AddTodoViewModel = hiltViewModel()
) {
    var currentPos by remember { mutableStateOf(0) }
    var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(item.title))
    }
    val (focusRequester) = FocusRequester.createRefs()
    LaunchedEffect(key1 = vm.currentFocusRequestId.value){
        if(item.uid == vm.currentFocusRequestId.value){
            focusRequester.requestFocus()
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = {
                vm.onCheckableDelete(item)
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
                focusedElevation = 4.dp
            )
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Checkbox(
                    checked = item.isCompleted,
                    onCheckedChange = {
                        vm.onCheckableCheck(item,it)
                    }
                )
                TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it.copy(text = it.text.trim())
                        vm.onCheckableValueChange(item,textFieldValue.text)
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
                            text = "items",
                            fontStyle = FontStyle.Italic
                        )
                    },
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .onKeyEvent {
                            Log.d("flfklskf", it.nativeKeyEvent.keyCode.toString())
                            if (it.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                                vm.onAddCheckable(item)
                                true
                            } else if (it.nativeKeyEvent.keyCode == KEYCODE_DEL) {
//                                vm.onBackOnValue(
//                                    item,
//                                    currentPos,
//                                    textFieldValue.selection.start
//                                )
                                currentPos = textFieldValue.selection.start
                                true
                            } else {
                                false
                            }

                        }
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                vm.onFocusGot(item)
                            }
                        },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )
            }
        }
        Row(
            modifier = Modifier
        ){
            IconButton(
                onClick = {
                    vm.onAddCheckable(item)
                },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Checkable",
                    tint = colorResource(id = R.color.main_color),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


