package com.example.notebook.feature_note.domain.use_cases

import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository
import com.example.notebook.feature_note.domain.utils.NoteOrder
import com.example.notebook.feature_note.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val noteRepository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
        searchQuery: String = ""
    ): Flow<List<Note>>{

        return noteRepository.getNotes()
            .map {note ->
                val filteredNotes = if (searchQuery.isNotBlank()) {
                    note.filter { note ->
                        val titleMatch = note.title.contains(searchQuery, ignoreCase = true)
                        val contentMatch = note.content.contains(searchQuery, ignoreCase = true)

                        titleMatch || contentMatch
                    }
                } else {
                    note
                }
                when (noteOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> filteredNotes.sortedBy { it.title.lowercase() }
                            is NoteOrder.Date -> filteredNotes.sortedBy { it.timestamp }
                            is NoteOrder.Color -> filteredNotes.sortedBy { it.color }
                        }
                    }
                    is OrderType.Descending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> filteredNotes.sortedByDescending { it.title }
                            is NoteOrder.Date -> filteredNotes.sortedByDescending { it.timestamp }
                            is NoteOrder.Color -> filteredNotes.sortedByDescending { it.color }
                        }
                    }
                }
            }
    }
}