package com.example.notebook.feature_secret_note.presentation

import com.example.notebook.feature_note.domain.model.Note


data class SecretNotesState(

    val isLoading :Boolean,
    val notes: List<Note> = emptyList()
)
