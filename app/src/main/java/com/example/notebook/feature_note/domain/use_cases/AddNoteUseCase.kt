package com.example.notebook.feature_note.domain.use_cases

import com.example.notebook.common.InvalidNoteException
//import com.example.notebook.feature_note.domain.model.InvalidNoteException
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend  operator fun invoke(note: Note){

        if (note.title.isBlank()){

            throw InvalidNoteException("Title cannot empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("Content cannot empty")
        }

        repository.insertNote(note)
    }
}