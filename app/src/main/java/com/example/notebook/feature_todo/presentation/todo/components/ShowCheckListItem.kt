package com.example.notebook.feature_todo.presentation.todo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.R
@Composable
fun ShowCheckListItem(
    checkList: ChecklistItem
) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){

            Checkbox(
                checked = checkList.isCompleted ,
                onCheckedChange ={
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = colorResource(id = R.color.main_color)
                )
            )

            Text(
                text = checkList.title,
                modifier = if(checkList.isCompleted)Modifier
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width, size.height / 2),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                else Modifier)
        }
}