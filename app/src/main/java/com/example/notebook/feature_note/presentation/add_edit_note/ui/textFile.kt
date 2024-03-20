package com.example.notebook.feature_note.presentation.add_edit_note.ui

import android.R
import android.annotation.SuppressLint
import android.util.Log
 import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.notebook.feature_note.presentation.add_edit_note.components.TwoColorDialog
import com.example.notebook.feature_note.presentation.add_edit_note.components.colourSaver
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreentesting() {
    Scaffold (){

        Box() {

            myScrollableColumn()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myScrollableColumn(){
    val colors = listOf(
        Color(0xFFEF9A9A),
        Color(0xFFF48FB1),
        Color(0xFF80CBC4),
        Color(0xFFA5D6A7),
        Color(0xFFFFCC80),
        Color(0xFFFFAB91),
        Color(0xFF81D4FA),
        Color(0xFFCE93D8),
        Color(0xFFB39DDB),
        Color(0xFFFFFFFF),
        Color(0xFF000000),
    )

    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    var currentlySelected by rememberSaveable(saver = colourSaver())
    {
        mutableStateOf(colors[0])
    }

    val state = rememberRichTextState()
    val stateTitle = rememberRichTextState()
    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        item {

            EditorControls(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                state = state,
                onBoldClick = {
                    state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                },
                onItalicClick = {
                    state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                },
                onUnderlineClick = {
                    state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                },
                onTitleClick = {
                    state.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                },
                onSubtitleClick = {
                    state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
                },
                onTextColorClick = {
                    colorPickerOpen = true
                },
                onStartAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                },
                onEndAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                },
                onCenterAlignClick = {
                    state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                },
                onExportClick = {
                    Log.d("Editor", state.toHtml())
                }
            )
        }

        item{

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                    focusedElevation = 12.dp,

                )
            ) {

                RichTextEditor(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    singleLine= true,
                    maxLines = 1,
                    state = stateTitle,
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = Color.White,
                        cursorColor = Color.Black
                    ),
                )
            }
        }
        item{
            RichTextEditor(
                modifier = Modifier
                    .height(1000.dp)
                    .fillMaxWidth(),
                state = state,
                colors = RichTextEditorDefaults.richTextEditorColors(
                    containerColor = Color.White,
                    cursorColor = Color.Black
                )
            )
        }
    }


    if (colorPickerOpen) {
        TwoColorDialog(
            colorList = colors,
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = {
                currentlySelected = it
                colorPickerOpen = false
                state.toggleSpanStyle(SpanStyle(color = currentlySelected))
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    state: RichTextState,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onTextColorClick: () -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onExportClick: () -> Unit,
) {

    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var linkSelected by rememberSaveable { mutableStateOf(false) }
//    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }
    var alignmentSelected by rememberSaveable {
        mutableStateOf(1)
    }




    var showLinkDialog by remember { mutableStateOf(false) }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = textColorSelected,
            onChangeClick = { textColorSelected = it },
            onClick = onTextColorClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = linkSelected,
            onChangeClick = { linkSelected = it },
            onClick = { showLinkDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.AddLink,
                contentDescription = "Link Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = true,
            selectedColor = MaterialTheme.colorScheme.tertiary,
            onChangeClick = { },
            onClick = onExportClick
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Export Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

