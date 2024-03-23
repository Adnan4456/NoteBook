package com.example.notebook.feature_note.presentation.notes.components


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.notebook.R
import com.example.notebook.feature_note.domain.model.Note

data class DropDownItem(
    val text:String,
    val icon: ImageVector
)

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize:  Dp = 30.dp,
    onDeleteClick : () -> Unit,
    onBookMarkChange: () -> Unit,
    onSecretClick: ()->Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val icon  = if(note.isBookMarked) Icons.Default.BookmarkRemove
                else Icons.Outlined.BookmarkAdd

    var isPlaying by remember { mutableStateOf(false) }

    var isDropDownVisible by remember { mutableStateOf(false) }
    var selectedDropDownItem by remember { mutableStateOf("") }

    var pressOffset by remember{

        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current


    LaunchedEffect(note.isBookMarked) {
        isPlaying = true
    }

    val dropdownItems = listOf(
        DropDownItem(
            "Favourite",
            Icons.Filled.Star
        ),
        DropDownItem(
            "Hide",
            Icons.Filled.VisibilityOff
        ),
        DropDownItem(
            "Delete",
            Icons.Filled.Delete
        )
    )

    Box(
        modifier = modifier
            .animateContentSize()
    ) {
        Card(
            colors =CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.all_notes_item)
            ),
            shape = RoundedCornerShape(8.dp),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    )
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {

                AsyncImage(
                    model = note.imageBitmap,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged {
                            itemHeight = with(density) {
                                it.height.toDp()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Image(
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .pointerInput(true) {
                                detectTapGestures(
                                    onPress = {
                                        isDropDownVisible = true
                                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                    }

                                )
                            },
                        painter = painterResource(id = R.drawable.menu_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Fit)
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(painter = painterResource(id = R.drawable.menu_icon),
//                            contentDescription = "Drop down")
//                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                Divider(
                    modifier = Modifier.padding(1.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = .5f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines =  if (expanded) 10 else 5,
                    overflow = TextOverflow.Ellipsis
                )


            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TextButton(
                    onClick = {
                        expanded = !expanded
                    })
                {
                    Text(text = if (expanded) "Read Less" else "Read More" ,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    )
                }
            }

            DropdownMenu(
                expanded = isDropDownVisible,
                onDismissRequest = {
                    isDropDownVisible = false
                },
                offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight * 5,
                    x = pressOffset.x + itemHeight * 5
                ),
            ){
                dropdownItems.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.padding(8.dp),
                        leadingIcon = {
                            Icon(imageVector = it.icon, contentDescription = "icons")
                        },
                        text = {
                               Text(text = it.text)
                        },
                        onClick = {

                            Log.d("TAG","${it.text} clicked")

                            if(it.text.equals("Delete")){
                                onDeleteClick.invoke()
                            } else if(it.text.equals("Hide")){
                                onSecretClick.invoke()
                            }else if (it.text.equals("Favourite")){
                                onBookMarkChange.invoke()
                            }
                            isDropDownVisible = false
                        },
                        trailingIcon = {

                            if(note.isBookMarked){
                                Icons.Filled.SelectAll
                            }
                        }
                    )
                }
            }



//            Row (
//                modifier  = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//
//              ElevatedCard(
//                  elevation = CardDefaults.cardElevation(
//                      defaultElevation = 5.dp
//                  )
//              ) {
//                  IconButton(onClick = {
//                      onSecretClick.invoke()
//                  }) {
//                      Icon(imageVector = Icons.Default.VisibilityOff ,
//                          contentDescription = "Secret Note")
//
//                  }
//              }
//
//                ElevatedCard (
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 5.dp
//                    )
//                        ){
//                    IconButton(
//
//                        onClick = {
//                            onBookMarkChange.invoke()
//                            isPlaying = true
//                        },
//                    ) {
//
//                        LottieAnimation(
//                            modifier = Modifier.size(30.dp),
//                            composition = composition,
//                            progress = if (isPlaying) 1f else 0f,
//                        )
//                        Icon(
//                            imageVector = icon,
//                            contentDescription = "Bookmark changed")
//                    }
//                }
//                ElevatedCard {
//                    IconButton(
//                        onClick = onDeleteClick,
//                    ) {
//                        Icon(imageVector = Icons.Default.Delete,
//                            contentDescription = "Note Deleted")
//                    }
//                }
//
//            }
        }
    }
 }