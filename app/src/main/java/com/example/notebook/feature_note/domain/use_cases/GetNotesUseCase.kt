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
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>>{

        return noteRepository.getNotes()
            .map {note ->
                when (noteOrder.orderType){
                    is OrderType.Ascending -> {
                        when(noteOrder){
                            is NoteOrder.Title -> note.sortedBy { it.title.lowercase() }
                            is NoteOrder.Date -> note.sortedBy { it.timestamp }
                            is NoteOrder.Color -> note.sortedBy { it.color }
                        }
                    }
                    is OrderType.Descending ->{
                        when(noteOrder){
                            is NoteOrder.Title -> note.sortedByDescending { it.title }
                            is NoteOrder.Date -> note.sortedByDescending { it.timestamp }
                            is NoteOrder.Color -> note.sortedByDescending { it.color }
                        }
                    }
                }

            }
    }
}