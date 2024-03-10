package com.example.notebook.feature_note.presentation.bookmarked_notes.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.GetBookMarkedNotesUseCase
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkEvent
import com.example.notebook.feature_note.presentation.bookmarked_notes.model.BookMarkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookMarkedViewModel
    @Inject constructor(
        private val getBookMarkedNotesUseCase: GetBookMarkedNotesUseCase,
        private val noteUseCases: NoteUseCases,
):ViewModel() {



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
                delay(1000)
                _noteList.update {
                    it.copy(isLoading = false , notes = notes )
                }

            }.launchIn(viewModelScope)
    }

    fun onEvent(event : BookMarkEvent){
        when(event){
            is BookMarkEvent.onBookMark ->{
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isBookMarked = !event.note.isBookMarked))
                }
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
            is BookMarkEvent.MakeSecret ->{
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isSecrete = !event.note.isSecrete))
                }
            }
        }
    }
}