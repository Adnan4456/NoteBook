package com.example.notebook.feature_note.presentation.notes

import androidx.lifecycle.ViewModel
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.notes.components.NotesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
):ViewModel() {

    

    fun onEvent(event:NotesEvent){
        when(event){
            is NotesEvent.Order -> {

            }
            is NotesEvent.DeleteNote -> {

            }
            is NotesEvent.RestoreNote -> {
            }
            is NotesEvent.ToggleOrderSection -> {


            }
        }
    }

}