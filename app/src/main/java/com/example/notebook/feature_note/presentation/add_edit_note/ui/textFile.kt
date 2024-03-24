package com.example.notebook.feature_note.presentation.add_edit_note.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.LocalFullyDrawnReporterOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.notebook.feature_note.presentation.add_edit_note.components.TwoColorDialog
import com.example.notebook.feature_note.presentation.add_edit_note.components.colourSaver
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.example.notebook.R
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.notebook.feature_note.presentation.notes.NotesEvent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreentesting(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
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


    val state = rememberRichTextState()

    val context = LocalContext.current

    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }

    var currentlySelected by rememberSaveable(saver = colourSaver())
    {
        mutableStateOf(colors[0])
    }

    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize

    val scaffoldState = rememberBottomSheetScaffoldState()

    val scope   = rememberCoroutineScope()
    var inputStream: InputStream


    val pickPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri ->

            if(uri != null){
                inputStream = context.getContentResolver().openInputStream(uri)!!
                viewModel.bitmap.value = BitmapFactory.decodeStream(inputStream)
            }

        })


    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event ->
            when(event){
                is AddEditNoteViewModel.UiEvent.ShowSnackBar ->{
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.UiEvent.SaveNote ->{
                    navController.navigateUp()
                }
            }
        }
    }

    var bottomsheet by rememberSaveable{
        mutableStateOf(false)
    }

    val color = Color(0xDEDEDEDE)

    Surface{

        val systemUiController = rememberSystemUiController()
        val darkTheme = isSystemInDarkTheme()

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = color
            )
        }

        BottomSheetScaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(
                        id = R.color.edit_notes_bg
                    )
                ),
            sheetContainerColor = colorResource(id = R.color.app_black),
            scaffoldState = scaffoldState,
            sheetContent ={
                TextBottomSheet(
                    onDismiss = {
                        bottomsheet = false
                    },
                    onBoldClick = {

                        viewModel.editorContentState.toggleSpanStyle(
                            SpanStyle(fontWeight = FontWeight.Bold)
                        )
                    },
                    onItalicClick = {
                        viewModel.editorContentState.toggleSpanStyle(
                            SpanStyle(fontStyle = FontStyle.Italic)
                        )
                    },
                    onUnderlineClick = {
                        viewModel.editorContentState.toggleSpanStyle(
                            SpanStyle(textDecoration = TextDecoration.Underline)
                        )
                    },
                    onTitleClick = {
                        viewModel.editorContentState.toggleSpanStyle(
                            SpanStyle(fontSize = titleSize)
                        )
                    },
                    onSubtitleClick = {
                        viewModel.editorContentState.toggleSpanStyle(
                            SpanStyle(fontSize = subtitleSize)
                        )
                    },
                    onTextColorClick = {
                        colorPickerOpen = true
                    },
                    onStartAlignClick = {
                        viewModel.editorContentState.toggleParagraphStyle(
                            ParagraphStyle(textAlign = TextAlign.Start)
                        )
                    },
                    onEndAlignClick = {
                        viewModel.editorContentState.toggleParagraphStyle(
                            ParagraphStyle(textAlign = TextAlign.End)
                        )
                    },
                    onCenterAlignClick = {
                        viewModel.editorContentState.toggleParagraphStyle(
                            ParagraphStyle(textAlign = TextAlign.Center)
                        )
                    },
                    onExportClick = {
                    }
                )
            },
            sheetPeekHeight = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = colorResource(id = R.color.edit_notes_bg)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Header(viewModel)
                    Spacer(modifier = Modifier.height(30.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){

                        item{
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(.5f)
                                    .height(200.dp)
                                    .clickable {
                                    },
                            ){

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
                                            model = viewModel.bitmap.value,
                                            contentDescription = "",
                                            contentScale = ContentScale.FillBounds,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                            }
                        }



                        item{
                            Spacer(modifier = Modifier.height(16.dp))

                            RichTextEditor(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine= true,
                                maxLines = 1,
                                state = viewModel.editorTitleState,
                                supportingText = { Text(text = "Content")},
                                colors = RichTextEditorDefaults.richTextEditorColors(
                                    containerColor = colorResource(id = R.color.edit_notes_bg),
                                    cursorColor = Color.Black
                                ),
                            )
                            LaunchedEffect(viewModel.editorTitleState.annotatedString) {
                                // This code will run each time the rich text is changed.
                                Log.d("TAG", "${this}")
                            }
                        }
                        item{
                            RichTextEditor(
                                modifier = Modifier
                                    .height(600.dp)
                                    .fillMaxWidth(),
                                state = viewModel.editorContentState,
                                colors = RichTextEditorDefaults.richTextEditorColors(
                                    containerColor = colorResource(id = R.color.edit_notes_bg),
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
                                viewModel.editorContentState.toggleSpanStyle(SpanStyle(color = currentlySelected))
                                colorPickerOpen = false
                            }
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.app_black)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(

                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(30.dp),
                                onClick = {
                                    pickPhotoLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }) {

                                Icon(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(20.dp),
                                    painter = painterResource(id = R.drawable.add_image),
                                    contentDescription = "",
                                    tint = Color.White)
                            }
                            IconButton(
                                modifier = Modifier
                                    .height(50.dp)
                                    .width(30.dp),
                                onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }

                                }) {

                                Icon(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(20.dp),
                                    painter = painterResource(id = R.drawable.bx_text),
                                    contentDescription = "",
                                    tint = Color.White)
                            }
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(20.dp),
                                painter = painterResource(id = R.drawable.menu_icon),
                                contentDescription = "",
                                tint = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(viewModel: AddEditNoteViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

       Row(
           verticalAlignment = Alignment.CenterVertically
       ) {
           Icon(
               imageVector = Icons.Default.ArrowBack,
               contentDescription = "",
               tint = colorResource(id = R.color.app_blue)
           )
           TextButton(
               onClick = {
               }) {
               Text(text = "Back" ,
                   style = TextStyle(
                       fontSize = 18.sp,
                       color = colorResource(id = R.color.app_blue)
                   )
               )
           }
       }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

//            Icon(
//                modifier = Modifier
//                    .height(25.dp)
//                    .width(25.dp),
//                imageVector = Icons.Default.Mic,
//                contentDescription = "",
//            tint = colorResource(id = R.color.app_black))

            Spacer(modifier = Modifier.width(16.dp))

            TextButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(37.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    Log.d("richtext" , "${viewModel.editorTitleState.toString()}")
                          viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.app_black)
                )
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                    color = colorResource(id = R.color.box_color)
                ))
            }
        }
    }
}



@Composable
fun TextBottomSheet(
    onDismiss: (() -> Unit),
    onItalicClick:()->Unit,
    onBoldClick: ()->Unit,
    onUnderlineClick:()->Unit,
    onTitleClick:()->Unit,
    onSubtitleClick:() ->Unit,
    onTextColorClick: () -> Unit,
    onStartAlignClick:() -> Unit,
    onEndAlignClick:() -> Unit,
    onCenterAlignClick:() -> Unit,
    onExportClick:() -> Unit
){

    val state = rememberRichTextState()



    EditorControls(
        modifier = Modifier
            .fillMaxWidth(),
        state = state,
        onBoldClick = {
            onBoldClick.invoke()
        },
        onItalicClick = {
            onItalicClick.invoke()
        },
        onUnderlineClick = {
            onUnderlineClick.invoke()
        },
        onTitleClick = {
            onTitleClick.invoke()
        },
        onSubtitleClick = {
            onSubtitleClick.invoke()
        },
        onTextColorClick = {
            onTextColorClick.invoke()
        },
        onStartAlignClick = {
            onStartAlignClick.invoke()
        },
        onEndAlignClick = {
            onEndAlignClick.invoke()
        },
        onCenterAlignClick = {
            onCenterAlignClick.invoke()
        },
        onExportClick = {
            Log.d("Editor", state.toHtml())
        }
    )

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
    viewModel: DialogButtonViewModel = hiltViewModel()
) {

    var showLinkDialog by remember { mutableStateOf(false) }

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 30.dp, start = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ControlWrapper(
            selected = viewModel.isBold.value,
            onChangeClick = {
//                boldSelected = it
                viewModel.isBold.value = it
                            },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.italicSelected.value,
            onChangeClick = {  viewModel.italicSelected.value = it },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected =  viewModel.underlineSelected.value,
            onChangeClick = {  viewModel.underlineSelected.value = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.titleSelected.value,
            onChangeClick = { viewModel.titleSelected.value = it },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.subtitleSelected.value,
            onChangeClick = { viewModel.subtitleSelected.value = it },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.textColorSelected.value,
            onChangeClick = { viewModel.textColorSelected.value = it },
            onClick = onTextColorClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.linkSelected.value,
            onChangeClick = { viewModel.linkSelected.value = it },
            onClick = { showLinkDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.AddLink,
                contentDescription = "Link Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.alignmentSelected.value == 0,
            onChangeClick = { viewModel.alignmentSelected.value = 0 },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.alignmentSelected.value == 1,
            onChangeClick = { viewModel.alignmentSelected.value = 1 },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = viewModel.alignmentSelected.value == 2,
            onChangeClick = { viewModel.alignmentSelected.value = 2 },
            onClick = onEndAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = colorResource(id = R.color.selected),
    unselectedColor: Color =  colorResource(id = R.color.un_selected),
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 16.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
//            .border(
//                width = 1.dp,
//                color = Color.LightGray,
//                shape = RoundedCornerShape(size = 6.dp)
//            )
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

data class BottomIcons(
    val title: String,
    val icon : ImageVector
)