package com.example.notebook.feature_note.domain.use_cases

import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase (
        private val noteRepository: NoteRepository
        ){

    suspend operator fun invoke(note: Note){

        noteRepository.deleteNote(note)
    }
}