package com.example.notebook.feature_secret_note.domain.use_case

import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSecretNotesUseCase
@Inject constructor(
    private val noteRepository: NoteRepository
)
{

    operator fun invoke(): Flow<List<Note>> {
        return noteRepository.getSecreteNotes()
    }
}