package com.example.notebook.feature_note.presentation.notes
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.utils.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}