package com.example.notebook.feature_note.presentation.notes.components

import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.utils.NoteOrder
import com.example.notebook.feature_note.domain.utils.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)