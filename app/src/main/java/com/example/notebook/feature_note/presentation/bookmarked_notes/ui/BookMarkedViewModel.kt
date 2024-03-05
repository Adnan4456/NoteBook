package com.example.notebook.feature_note.presentation.bookmarked_notes.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.GetBookMarkedNotesUseCase
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkEvent
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookMarkedViewModel
    @Inject constructor(
        private val getBookMarkedNotesUseCase: GetBookMarkedNotesUseCase,
        private val noteUseCases: NoteUseCases,
):ViewModel() {


    private val _state= mutableStateOf(BookMarkState(true))
    var state = _state

    private val _noteList = MutableStateFlow(BookMarkState(true))
    val noteList:StateFlow<BookMarkState> = _noteList.asStateFlow()

    private var getNotesJob: Job? = null
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes()
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = getBookMarkedNotesUseCase.invoke()
            .onEach {notes ->
                _state.value = state.value.copy(
                    notes = notes
                )
            }.launchIn(viewModelScope)
    }

    fun onEvent(event : BookMarkEvent){
        when(event){
            is BookMarkEvent.onBookMark ->{
                event.note.copy(isBookMarked = !event.note.isBookMarked)
            }
            is BookMarkEvent.onDelete ->{
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is BookMarkEvent.RestoreNote ->{
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
        }
    }
}