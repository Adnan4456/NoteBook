package com.example.notebook.feature_note.presentation.bookmarked_notes.model

import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.presentation.notes.NotesEvent

sealed class BookMarkEvent {

    object RestoreNote: BookMarkEvent()

    data class onBookMark(val note: Note): BookMarkEvent()
    data class onDelete(val note: Note): BookMarkEvent()

    data class MakeSecret(val note: Note): BookMarkEvent()
}