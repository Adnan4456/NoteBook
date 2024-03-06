package com.example.notebook.feature_note.domain.use_cases

import android.util.Log
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetBookMarkedNotesUseCase
@Inject constructor(
    private val noteRepository: NoteRepository
    ){
    operator fun invoke(): Flow<List<Note>> {
        Log.d("data","book maked repository")
        return noteRepository.getBookMarkedNotes()
    }
}