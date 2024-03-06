package com.example.notebook.feature_note.presentation.notes.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.notebook.R
import com.example.notebook.feature_note.domain.model.Note


@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize:  Dp = 30.dp,
    onDeleteClick : () -> Unit,
    onBookMarkChange: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val icon  = if(note.isBookMarked) Icons.Default.BookmarkRemove
                else Icons.Outlined.BookmarkAdd

    var isPlaying by remember { mutableStateOf(false) }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.bookmark)
    )


    LaunchedEffect(note.isBookMarked) {
        isPlaying = true
    }

    Box(
        modifier = modifier
            .animateContentSize()
    ) {
        Canvas(
            modifier = modifier
                .matchParentSize(),
        ) {

            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {

                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                clipPath(clipPath) {

                    drawRoundRect(
                        color = Color(
                            ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                        ),
                        topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                        size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            } // end clipPath
        } // end canvas
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .padding(end = 16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines =  if (expanded) 10 else 5,
                overflow = TextOverflow.Ellipsis
            )

            TextButton(
                onClick = {
                    expanded = !expanded
                })
            {
                Text(text = if (expanded) "Read Less" else "Read More" ,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

        Row (
            modifier  = Modifier.align(Alignment.BottomEnd)
                ){
            IconButton(

                onClick = {
                    onBookMarkChange.invoke()
                    isPlaying = true
                },
            ) {

                LottieAnimation(
                    modifier = Modifier.size(30.dp),
                    composition = composition,
                    progress = if (isPlaying) 1f else 0f,
                )
                Icon(
                    imageVector = icon,
                    contentDescription = "Bookmark changed")
            }

            IconButton(
                onClick = onDeleteClick,
            ) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Note Deleted")
            }
        }
    }
 }
