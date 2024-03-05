package com.example.notebook.feature_note.presentation.bookmarked_notes.model

import com.example.notebook.feature_note.domain.model.Note


data class BookMarkState(

    val isLoading :Boolean,
    val notes: List<Note>? = null
)
