package com.example.notebook.feature_note.presentation.add_edit_note.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_note.domain.model.InvalidNoteException
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.example.notebook.feature_note.presentation.add_edit_note.NoteTextFieldState
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

    private val _isBookmarked = mutableStateOf(false)


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
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                        _isBookmarked.value = note.isBookMarked
                        _isSecret.value = note.isSecrete
                    }
                }
            }
        }
    }


    fun onEvent(event:AddEditNoteEvent){

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
                viewModelScope.launch {
                    try {

                        noteUseCase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId,
                                isBookMarked = _isBookmarked.value,
                                isSecrete =  _isSecret.value

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