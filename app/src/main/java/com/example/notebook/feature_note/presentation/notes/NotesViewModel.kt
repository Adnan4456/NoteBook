package com.example.notebook.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.domain.utils.NoteOrder
import com.example.notebook.feature_note.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel
    @Inject constructor(
    private val noteUseCases: NoteUseCases,

):ViewModel() {


    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesEvent.Bookmark -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isBookMarked = !event.note.isBookMarked))
                }
            }
            is NotesEvent.Search -> {
                onSearchQueryChanged(event.query)
            }
            is NotesEvent.MakeSecret -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isSecrete = !event.note.isSecrete))
                }
            }
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchNotes(query, state.value.noteOrder)
    }
    private fun searchNotes(query: String, noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder, query)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }
}