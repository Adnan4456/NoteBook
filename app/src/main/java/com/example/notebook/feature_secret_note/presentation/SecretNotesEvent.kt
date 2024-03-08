package com.example.notebook.feature_secret_note.presentation

import com.example.notebook.feature_note.domain.model.Note

sealed class SecretNotesEvent {

    object RestoreNote: SecretNotesEvent()

    data class onBookMark(val note: Note): SecretNotesEvent()
    data class onDelete(val note: Note): SecretNotesEvent()

    data class MakeUnSecret(val note: Note): SecretNotesEvent()
}