package com.example.notebook.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun TransparentHintTextField(
    text:String,
    hint:String,
    modifier: Modifier = Modifier,
    isHintVisible:Boolean = true,
    onValueChanged : (String) -> Unit,
    textStyle:TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange : (FocusState) -> Unit
){

    Box(
        modifier = modifier
            .padding(8.dp)
            .border(
                1.dp,
                Color.DarkGray
            ),


    ){
        BasicTextField(
            value = text,
            onValueChange = onValueChanged,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if (isHintVisible){
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}

@Composable
fun TransparentContentTextField(
    text:String,
    hint:String,
    modifier: Modifier = Modifier,
    isHintVisible:Boolean = true,
    onValueChanged : (String) -> Unit,
    textStyle:TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange : (FocusState) -> Unit
){

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = .5.dp,
                Color.DarkGray
            ),

        contentAlignment = Alignment.TopStart
    ){
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            BasicTextField(
                value = text,
                onValueChange = onValueChanged,
                textStyle = textStyle,
                singleLine = singleLine,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .onFocusChanged {
                        onFocusChange(it)
                    }
            )
        }

        if (isHintVisible){
            Text(text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }

}

