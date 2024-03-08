package com.example.notebook.feature_secret_note.presentation.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_secret_note.domain.use_case.GetSecretNotesUseCase
import com.example.notebook.feature_secret_note.presentation.SecretNotesEvent
import com.example.notebook.feature_secret_note.presentation.SecretNotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SecretNoteViewModel
    @Inject constructor(
        private val secretNoteUseCase: GetSecretNotesUseCase,
        private val noteUseCases: NoteUseCases,
    ): ViewModel() {


    private val _notesList = MutableStateFlow(SecretNotesState(true))
    var notesList: StateFlow<SecretNotesState> = _notesList.asStateFlow()

    private var getNotesJob: Job? = null
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes()
    }

    private fun getNotes() {

        getNotesJob?.cancel()
        getNotesJob = secretNoteUseCase.invoke()
            .onEach {notes ->
                delay(2000)
                _notesList.update {
                    it.copy(isLoading = false , notes = notes )
                }

            }.launchIn(viewModelScope)
    }


    fun onEvent(event : SecretNotesEvent){
        when(event){
            is SecretNotesEvent.onBookMark -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isBookMarked = !event.note.isBookMarked))
                }
            }
            is SecretNotesEvent.onDelete ->{
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is SecretNotesEvent.MakeUnSecret -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note.copy(isSecrete = !event.note.isSecrete))
                }
            }
            is SecretNotesEvent.RestoreNote ->{
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
        }


    }
}
