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
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

data class DropDownItem(
    val text:String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
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


    var isPlaying by remember { mutableStateOf(false) }

    val stateContent = rememberRichTextState()

    val stateTitle =  rememberRichTextState()

    stateTitle.setHtml(note.title)
    stateContent.setHtml(note.content)

    var isDropDownVisible by remember { mutableStateOf(false) }

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
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.all_notes_item)
            ),
            shape = RoundedCornerShape(8.dp),
        ) {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(id = R.color.all_notes_item)
                    )
            ) {
                Card (
                    colors = CardDefaults.cardColors(
                    ),
                    elevation =CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp,
                        focusedElevation = 6.dp
                    ),
                    shape = RoundedCornerShape(8.dp)
                ){

                    AsyncImage(
                        model = note.imageBitmap,
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .onSizeChanged {
                            itemHeight = with(density) {
                                it.height.toDp()
                            }
                        },

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    //Title
                    RichTextEditor(
//                        modifier = Mo.weight(.7f),
                        enabled = true,
                        state = stateTitle,
                        readOnly = true,
                        singleLine= true,
                        maxLines = 1,
                        colors = RichTextEditorDefaults.richTextEditorColors(
                            containerColor = colorResource(id = R.color.all_notes_item)

                        )
                    )

//                    Image(
//                        modifier = Modifier
//                            .height(20.dp)
//                            .width(20.dp)
//                            .pointerInput(true) {
//                                detectTapGestures(
//                                    onPress = {
//                                        isDropDownVisible = true
//                                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
//                                    }
//
//                                )
//                            },
//                        painter = painterResource(id = R.drawable.menu_icon),
//                        contentDescription = "",
//                        contentScale = ContentScale.Fit)
                }
                Spacer(modifier = Modifier.height(4.dp))

                //content description
                RichTextEditor(
                    enabled = true,
                    readOnly = true,
                    state = stateContent,
                    maxLines =  if (expanded) 4 else 1,
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = colorResource(id = R.color.all_notes_item)
                    ),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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

                Image(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .pointerInput(true) {
                            detectTapGestures(
                                onPress = {
                                    isDropDownVisible = true
                                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                    Log.d("offset","${pressOffset.x} and  ${pressOffset.y}")
                                }
                            )
                        },
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit)
            }

            DropdownMenu(
                expanded = isDropDownVisible,
                onDismissRequest = {
                    isDropDownVisible = false
                },
                offset = pressOffset.copy(
                    y = pressOffset.y  ,
                    x = pressOffset.x
                ),
            ){
                dropdownItems.forEach {
                    DropdownMenuItem(
                        modifier = Modifier.padding(4.dp),
                        leadingIcon = {
                            Icon(imageVector = it.icon, contentDescription = "icons")
                        },
                        text = {
                               Text(text = it.text ,
                               style = TextStyle(
                                   fontSize = 16.sp,
                               ))
                        },
                        onClick = {

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