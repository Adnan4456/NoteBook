package com.example.notebook.feature_note.presentation.notes.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.notebook.R

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

    SearchBar(
        modifier= Modifier
            .fillMaxWidth(),
        colors = SearchBarDefaults.colors(
            containerColor = Color.White
        ),
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
        tonalElevation = 16.dp,
    ) {
    }
}

@Composable
fun ClickableCard(
    modifier: Modifier,
    text:String ,
    onClick: () -> Unit ,
    isSelected: Boolean,
    color: Color) {

    val animatedColor = animateColorAsState(
        targetValue = if (isSelected) color else Color.White,
        animationSpec = tween(durationMillis = 500)
    )

    Surface(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(8.dp)
            .height(50.dp),
        color = animatedColor.value,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(
                modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(50.dp),
                contentAlignment = Alignment.Center
            ){

                if(text.equals("All Notes")){
                    Image(
                        modifier =Modifier.fillMaxSize(.6f),
                        painter =  painterResource(id = R.drawable.all_notes_bg ),
                        contentDescription = "")

                    Image(
                        modifier =Modifier.fillMaxSize(.4f),
                        painter =  painterResource(id = R.drawable.all_notes),
                        contentDescription = "")
                }

                if(text.equals("Trash")){
                    Image(
                        modifier =Modifier.fillMaxSize(.6f),
                        painter =  painterResource(id = R.drawable.delete_icon_bg ),
                        contentDescription = "")


                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = Color.White)
                }

                if(text.equals("Favourites")){
                    Image(
                        modifier =Modifier.fillMaxSize(.6f),
                        painter =  painterResource(id = R.drawable.fav_icon_bg ),
                        contentDescription = "")


                    Icon(imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = Color.White)
                }

                if(text.equals("Hidden Notes")){
                    Image(
                        modifier =Modifier.fillMaxSize(.6f),
                        painter =  painterResource(id = R.drawable.hidden_icon_bg ),
                        contentDescription = "")

                    Icon(imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "",
                    tint = Color.White)
                }
            }

            Text(text = text,
                color = if (isSelected)   Color.White else Color.Black)
        }
    }
}
@Composable
fun CardRow(onClick: (String) -> Unit, modifier: Modifier) {

    var selectedCardIndex by remember { mutableStateOf(-1) }
    val cardTexts = listOf("All Notes", "Hidden Notes","Favourites", "Trash")
    val cardColors = listOf(Color.Gray, Color.Blue, Color.Yellow, Color.Red)


        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (index in cardTexts.indices step 2) {
                Column(
                    modifier = Modifier.weight(.5f),
                ){
                    ClickableCard(
                        modifier = Modifier.fillMaxWidth()                     ,
                        text = cardTexts.getOrElse(index) { "" },
                        onClick = {
                            selectedCardIndex = index
                            onClick(cardTexts[index])
                        },
                        isSelected = index == selectedCardIndex,
                        color = cardColors.getOrElse(index) { Color.White }
                    )

                    if (index + 1 < cardTexts.size) {
                        ClickableCard(
                            modifier = Modifier.fillMaxWidth(),
                            text = cardTexts.getOrElse(index + 1) { "" },
                            onClick = {
                                selectedCardIndex = index + 1
                                onClick(cardTexts[index + 1])
                            },
                            isSelected = index + 1 == selectedCardIndex,
                            color = cardColors.getOrElse(index + 1) { Color.White }
                        )
                    }
                }
            }
        }
}

