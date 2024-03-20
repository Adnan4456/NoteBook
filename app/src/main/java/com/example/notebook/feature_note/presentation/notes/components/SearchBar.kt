package com.example.notebook.feature_note.presentation.notes.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearchQuery: (String) -> Unit
) {

    var query by remember {
        mutableStateOf("")
    }

    var active by remember {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current

    DockedSearchBar(
        modifier= Modifier.fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(16.dp),
        query = query,
        onQueryChange = {
            query  = it
            onSearchQuery(query)
        },
        onSearch = {
            //press search icon on keybpard
            active = false
            focusManager.clearFocus()

        },
        active = active,
        placeholder = {
                      Text(text = "Search")
        },
        onActiveChange = {
            active = it
        },
        colors =SearchBarDefaults.colors(
            containerColor = Color.White
        ),
        trailingIcon = {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {

                Icon(imageVector = Icons.Default.Mic, contentDescription = "")

                Spacer(modifier = Modifier.width(8.dp))
                if (active){
                    Icon(
                        modifier = Modifier.clickable {

                            if (query.isEmpty()){
                                active = false
                                focusManager.clearFocus()
                            }else
                            {
                                query = ""
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription ="Close icon")
                }
            }

        },
        tonalElevation = 8.dp,


    ) {
    }
}

@Composable
fun ClickableCard( text:String , onClick: () -> Unit , isSelected: Boolean, color: Color) {

    val animatedColor = animateColorAsState(
        targetValue = if (isSelected) color else Color.LightGray,
        animationSpec = tween(durationMillis = 1000)
    )

    Surface(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(16.dp)
            .height(50.dp)
            .width(150.dp),
        color = animatedColor.value,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 8.dp
    ) {

        Box(
            modifier= Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row() {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                Text(text = text,
                    color = if (isSelected)   Color.White else Color.Black)
            }
        }
    }
}

@Composable
fun CardRow(onClick: (String) -> Unit) {

    var selectedCardIndex by remember { mutableStateOf(-1) }
    val cardTexts = listOf("AllNotes", "Hidden Notes","Favourites", "Trash")
    val cardColors = listOf(Color.Gray, Color.Blue, Color.Yellow, Color.Red)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (index in cardTexts.indices step 2) {
                Column(

                ){
                    ClickableCard(
                        text = cardTexts.getOrElse(index) { "" },
                        onClick = {
                            selectedCardIndex = index
                            onClick(cardTexts[index])
                        },
                        isSelected = index == selectedCardIndex,
                        color = cardColors.getOrElse(index) { Color.LightGray }
                    )

                    if (index + 1 < cardTexts.size) {
                        ClickableCard(
                            text = cardTexts.getOrElse(index + 1) { "" },
                            onClick = {
                                selectedCardIndex = index + 1
                                onClick(cardTexts[index + 1])
                            },
                            isSelected = index + 1 == selectedCardIndex,
                            color = cardColors.getOrElse(index + 1) { Color.LightGray }
                        )
                    }
                }
            }
        }
    }
}

