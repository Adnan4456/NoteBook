package com.example.notebook.feature_note.presentation.bookmarked_notes.model

import com.example.notebook.feature_note.domain.model.Note

sealed class BookMarkEvent {

    object isLoading: BookMarkEvent()
    data class Success(val data: List<Note>): BookMarkEvent()
    data class onFailure(val e : Exception): BookMarkEvent()
}