package com.example.notebook.feature_note.presentation.add_edit_note.ui

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.common.InvalidNoteException
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.notebook.feature_note.presentation.add_edit_note.NoteTextFieldState
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel
@Inject constructor(
    private val noteUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
) :ViewModel()  {


    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))

    var editorTitleState by mutableStateOf(RichTextState())

    var editorContentState by mutableStateOf(RichTextState())
        private set
    fun updateTitleEditorState(newState: RichTextState) {
        editorTitleState = newState
    }
    fun updateContentEditorState(newState: RichTextState){
        editorContentState = newState
    }

    private val _isBookmarked = mutableStateOf(false)

    var bitmap  = mutableStateOf<Bitmap?>(null)

    private val _isSecret = mutableStateOf(false)

    val noteTitle : State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter content"
    ))

    val noteContent : State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int ? = null

    init {

        savedStateHandle.get<Int>("noteId")?.let {noteId ->
            if (noteId != -1){
                viewModelScope.launch {

                    noteUseCase.getNote(noteId)?.also {note ->
                        currentNoteId = note.id
//                        _noteTitle.value = noteTitle.value.copy(
//                            text = note.title,
//                            isHintVisible = false
//                        )
                        editorTitleState.setHtml(note.title)
                        editorContentState.setHtml(note.content)
//                        _noteContent.value = _noteContent.value.copy(
//                            text = note.content,
//                            isHintVisible = false
//                        )
//                        _noteColor.value = note.color
                        _isBookmarked.value = note.isBookMarked
                        _isSecret.value = note.isSecrete
                         bitmap.value = note.imageBitmap
                    }
                }
            }
        }
    }


    fun onEvent(event:AddEditNoteEvent){
      Log.d("TAG","${editorTitleState.annotatedString}")
        Log.d("TAG","${editorContentState.annotatedString}")
        when(event){
            is AddEditNoteEvent.EnterTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value =  noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnterContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
//               Log.d("toHtml","${ editorTitleState.toHtml()}")
//                Log.d("annotatedString","${ editorTitleState.annotatedString}")
//                Log.d("","${editorTitleState.}")
                viewModelScope.launch {
                    try {

                        noteUseCase.addNoteUseCase(
                            Note(
//                                title = noteTitle.value.text,
                                title = editorTitleState.toHtml(),
                                content =  editorContentState.toHtml(),
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId,
                                isBookMarked = _isBookmarked.value,
                                isSecrete =  _isSecret.value,
                                imageBitmap = bitmap.value,
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?:" Unknow error."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message: String):UiEvent()
        object  SaveNote: UiEvent()
    }
}