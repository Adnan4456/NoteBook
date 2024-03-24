package com.example.notebook.feature_note.presentation.notes.components


import android.os.Build
import android.text.Html
import android.text.Spanned
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
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.notebook.R
import com.example.notebook.components.NormalTextComponent
import com.example.notebook.components.formatTimestamp
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
                    ),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start

            ) {
//                Card (
//                    colors = CardDefaults.cardColors(
//                    ),
//                    elevation =CardDefaults.cardElevation(
//                        defaultElevation = 8.dp,
//                        pressedElevation = 4.dp,
//                        focusedElevation = 6.dp
//                    ),
//                    shape = RoundedCornerShape(8.dp)
//                ){
//
//
//                }
//
                AsyncImage(
                    model = note.imageBitmap,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .onSizeChanged {
//                            itemHeight = with(density) {
//                                it.height.toDp()
//                            }
//                        },
//
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ){
//                }
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Text(text = stateTitle.annotatedString.toString() ,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    if(note.imageBitmap == null){
                        Divider()
                    }
                    Text(
                        text = stateContent.annotatedString,
                        style = TextStyle(
                            fontSize = 14.sp),
                        maxLines =  if (expanded) 7 else 2,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = formatTimestamp(note.timestamp),
                    style = TextStyle(
                        fontSize = 12.sp),
                    maxLines =  1,
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
                ) {

                    TextButton(
                        onClick = {
                            expanded = !expanded
                        })
                    {
                        Text(text = if (expanded) "Read Less" else "Read More" ,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Image(
                        modifier = Modifier
                            .height(20.dp)
                            .width(30.dp)
                            .padding(end = 8.dp)
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
                }

            }
            DropdownMenu(
                expanded = isDropDownVisible,
                onDismissRequest = {
                    isDropDownVisible = false
                },
                offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight ,
                    x = pressOffset.x + itemHeight
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
        }
    }
 }